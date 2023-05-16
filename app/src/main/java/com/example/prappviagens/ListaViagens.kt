package com.example.prappviagens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ListaViagem() {
    Column(modifier = Modifier.padding(16.dp)) {
        for (i in 1..5) {
            TripItem(
                country = "País $i",
                startDate = "01/01/2022",
                endDate = "10/01/2022",
                expenses = 1000 * i
            )
        }
    }
}

@Composable
fun TripItem(country: String, startDate: String, endDate: String, expenses: Int) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_aviao),
            contentDescription = "ícone do avião",
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Fit
        )
        Column {
            Text(
                text = country,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "$startDate - $endDate",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Total de gastos: R$ $expenses",
                style = MaterialTheme.typography.body1.copy(color = Color.DarkGray)
            )
        }
    }
}
