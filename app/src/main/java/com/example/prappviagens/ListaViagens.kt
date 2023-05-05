package com.example.prappviagens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prappviagens.model.Travel

@Composable
fun ListaViagem() {
    Text(text = "Hello Lista Viagem!")
}

/**@Composable
fun AppTrip(){
    val travel = listOf(
        Travel("Rio de Janeiro","02/02/2023","02/04/2023",3450.0,0),
        Travel("Santa Catarina","09/04/2023","12/04/2023",670.15,1),
        Travel("São Paulo","12/04/2023","22/04/2023",1450.0,0),
        Travel("Ceara","25/04/2023","02/03/2023",3750.00,0),
    )

    LazyColumn(){
        items(items = travel){
            val iconReason = when (it.reason) {
                0 -> R.drawable.bussines
                else -> R.drawable.lazer
            }
            screenApp(travel = it, iconReason = iconReason)
        }
    }

}

@Composable
fun screenApp(travel: Travel, iconReason: Int){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row {
            Image(
                painter = painterResource(iconReason),
                contentDescription = "Icon of travels",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Row(modifier = Modifier.padding(4.dp)) {
                Column {
                    Text(
                        text = travel.destination,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${travel.startDate} --> ${travel.endDate}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${travel.value}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
}
**/