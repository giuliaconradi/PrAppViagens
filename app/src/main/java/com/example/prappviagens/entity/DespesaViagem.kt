package com.example.prappviagens.entity

data class  DespesaViagem (
    val id: Int = 0,
    val userID: Int,
    val destino: String,
    val data_inicial: String,
    val data_final: String,
    val reason: Int,
    val DespesaID: Int = 0,
    val viagemID: Int,
    var descricao: String,
    val valor: Float
    ) {
    }