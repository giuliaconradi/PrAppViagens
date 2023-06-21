package com.example.prappviagens.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Despesa(
        @PrimaryKey(autoGenerate = true) val DespesaID: Int = 0,
        val viagemID: Int,
        val descricao: String,
        val valor: Float
    ) {
}