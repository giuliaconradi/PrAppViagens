package com.example.prappviagens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prappviagens.entity.Viagem
import android.app.Application
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.entity.Despesa
import com.example.prappviagens.entity.DespesaViagem
import com.example.prappviagens.viewModel.RegisterNewDespesaViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModelFactory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun tela(
    viagem: Viagem,
    iconReason: Int,
    selecao: Boolean,
    viewModel: RegisterNewViagemViewModel,
    onCardClick: (Viagem) -> Unit,
    onNavigateMenuBar: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(viagem) },
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
                        text = viagem.destino,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${viagem.data_inicial} --> ${viagem.data_final}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${formatFloat(viagem.orcamento)}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }

        }

    }
    if (selecao) {
        moreExpenses(viagem, viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.updateExpenses(viagem.id, calculateExpense(viagem.orcamento,viewModel.orcamento))
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

fun formatFloat(number: Float): String {
    return String.format("%.2f", number)
}

// Uso:
fun calculateExpense(oldExpense: Float, newExpense: Float): Float {
    if (newExpense != 0.0f) {
        return oldExpense + newExpense
    } else {
        return oldExpense
    }
}


@Composable
fun moreExpenses(viagem: Viagem, viewModel: RegisterNewViagemViewModel) {
    var orcamento = viagem.orcamento
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
            onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
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
fun ListTravels(userID: String, onNavigateHome:() -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    val selecionarViagem = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravels(Integer.parseInt(userID))
    val viagem by viewModel.viagem.collectAsState()


    LazyColumn() {
        items(items = viagem.filter { selecionarViagem.value == null || it.id == selecionarViagem.value }) { viagem ->
            val iconReason = when (viagem.reason) {
                0 -> R.drawable.trabalho
                else -> R.drawable.lazer
            }
            tela(viagem, iconReason, selecionarViagem.value != null, viewModel, onCardClick = {
                selecionarViagem.value = it.id
            },
                onNavigateMenuBar = { selecionarViagem.value = null })
        }
    }
}
