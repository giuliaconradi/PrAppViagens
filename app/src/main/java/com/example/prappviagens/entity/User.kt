package com.example.prappviagens.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
   @PrimaryKey(autoGenerate = true) val id: Int =  0,
    val name: String,
    val email: String,
    val password: String
) {

}