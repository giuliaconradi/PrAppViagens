package com.example.prappviagens.model

class Travel {
    data class Travel(
        val destination: String,
        var startDate: String,
        var endDate: String,
        var value: Double,
        val reason: Int
    )
}