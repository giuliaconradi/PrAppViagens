package com.example.prappviagens.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prappviagens.dao.UserDao
import com.example.prappviagens.dao.ViagemDao
import com.example.prappviagens.entity.User
import com.example.prappviagens.entity.Viagem

@Database(entities = [User::class, Viagem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun viagemDao(): ViagemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(application: Application): AppDatabase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                application.applicationContext,
                AppDatabase ::class.java,
                "meu2-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }

}