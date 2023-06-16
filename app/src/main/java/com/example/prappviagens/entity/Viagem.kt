package com.example.prappviagens.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "viagem")
    data class Viagem(
        @PrimaryKey(autoGenerate = true) val id: Int =  0,
        val userID: String,
        val destino: String,
        val data_inicial: String,
        val data_final: String,
        val orcamento: Float,
        val reason: Int
    )