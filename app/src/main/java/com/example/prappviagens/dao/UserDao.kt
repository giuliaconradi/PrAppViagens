package com.example.prappviagens.dao

import androidx.room.*
import com.example.prappviagens.entity.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select * from user u order by u.name ")
    suspend fun getAll(): List<User>

    @Query("select * from user u where u.name = :name")
    suspend fun findByName(name: String): User

    @Query("select * from user u where u.name = :name and u.password = :password")
    suspend fun findByCredentials(name: String, password:String): List<User>



}