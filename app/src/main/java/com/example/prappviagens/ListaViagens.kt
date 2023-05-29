package com.example.prappviagens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prappviagens.entity.Viagem

/*@Composable
fun ListaViagem() {
    @Composable
    fun ViagemScreen() {
        val viagens = listOf(
            Viagem(1, "", "10/01/2023", "10/01/2023""10/01/2023"),
            Viagem(2, "", "20/02/2023", "10/01/2023""10/01/2023"),
            Viagem(3, "", "12/03/2023", "10/01/2023"1, 4),

       LazyColumn {
            items(Viagem) { viagem ->
                Viagem(viagem) {
                    // Ação para abrir outra tela com detalhes da viagem
                }
            }
        }
    }

    @Composable
    fun ViagemItem(viagem: Viagem, onItemClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick.invoke() }
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Destino: ${viagem.destino}",
                        modifier = Modifier.run {
                            (Alignment.TopStart)
                                                .padding(16.dp)
                        }
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 16.dp, end = 16.dp)
                    ) {
                        Text(text = "Data inicial: ${viagem.data_inicial}")
                        Text(text = "Data final: ${viagem.data_final}")
                    }
                    Text(
                        text = "Orçamento: ${viagem.orcamento}",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                    Button(
                        onClick = { onItemClick.invoke() },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .height(IntrinsicSize.Min)
                            .width(70.dp)
                    ) {
                        Text(text = "Detalhes")
                    }
                }
            }
        }
    }
    }
 */