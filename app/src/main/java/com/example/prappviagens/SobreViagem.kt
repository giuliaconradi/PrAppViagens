package com.example.prappviagens.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SobreViagem(){
    Column(Modifier.background(Color(0xFFB3E5FC))) {
        Text(text = "Aplicativo desenvolvido por Giulia Conradi :)")
    }
}