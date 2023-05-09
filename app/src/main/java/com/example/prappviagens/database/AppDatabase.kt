package com.example.prappviagens.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prappviagens.dao.UserDao
import com.example.prappviagens.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(application: Application): AppDatabase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                application.applicationContext,
                AppDatabase ::class.java,
                "meu-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }

}