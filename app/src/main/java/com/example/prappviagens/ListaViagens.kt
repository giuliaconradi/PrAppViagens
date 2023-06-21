package com.example.prappviagens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import com.example.prappviagens.entity.Viagem
import android.app.Application
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModelFactory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun telaViagem(
    vgm: Viagem,
    icone: Int,
    tipoViagem: Boolean,
    viewModel: RegisterNewViagemViewModel,
    onCardClick: (Viagem) -> Unit,
    onNavigateMenuBar: () -> Unit,
)

{
    fun formatarValores(valor: Float): String {
        val formato = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formato.format(valor)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(vgm) },
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(icone),
                contentDescription = "Icon of travels",
                modifier = Modifier
                    .size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = vgm.destino,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${vgm.data_inicial} --> ${vgm.data_final}",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Valor Total: ${formatarValores(vgm.orcamento)}",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }

    }
    if (tipoViagem) {
        moreExpenses(vgm, viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.updateExpenses(vgm.id, calcularDespesa(vgm.orcamento, viewModel.orcamento))
                    onNavigateMenuBar()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text("Atualizar")
            }
        }
    }

}

// Uso:
fun calcularDespesa(anterior: Float, nova: Float): Float {
    if (nova != 0.0f) {
        return anterior + nova
    } else {
        return anterior
    }
}


@Composable
fun moreExpenses(travels: Viagem, viewModel: RegisterNewViagemViewModel) {
    var orcamento = travels.orcamento
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = orcamento.toString(),
            onValueChange = { orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Despesa atual") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                }
            ),
            modifier = Modifier.padding(16.dp),
            enabled = false
        )
        OutlinedTextField(
            value = viewModel.orcamento.toString(),
            onValueChange = { newValue ->
                viewModel.orcamento = (newValue.toFloatOrNull()?.toString() ?: "") as Float
            },
            label = { Text("Nova Despesa") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                }
            ),
            modifier = Modifier.padding(16.dp)
        ).toString()
    }

}

@Composable
fun ListaViagens(userID: String, onNavigateHome:() -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    val selectedTravelId = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravels(Integer.parseInt(userID))
    val travels by viewModel.travels.collectAsState()


    LazyColumn() {
        items(items = travels.filter { selectedTravelId.value == null || it.id == selectedTravelId.value }) { viagem ->
            val icone = when (viagem.reason) {
                0 -> R.drawable.trabalho
                else -> R.drawable.lazer
            }
            telaViagem(viagem, icone, selectedTravelId.value != null, viewModel, onCardClick = {
                selectedTravelId.value = it.id
            },
                onNavigateMenuBar = { selectedTravelId.value = null })
        }
    }
}
