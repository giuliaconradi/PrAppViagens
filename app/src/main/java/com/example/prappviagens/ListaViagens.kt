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

@Composable
fun screen(
    travels: Viagem,
    iconReason: Int,
    isItemSelected: Boolean,
    viewModel: RegisterNewViagemViewModel,
    onCardClick: (Viagem) -> Unit,
    onNavigateHome: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(travels) },
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
                        text = travels.destino,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${travels.data_inicial} --> ${travels.data_final}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${formatFloat(travels.orcamento)}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }

        }

    }
    if (isItemSelected) {
        moreExpenses(travels, viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.updateExpenses(travels.id, calculateExpense(travels.orcamento,viewModel.orcamento))
                    onNavigateHome()
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
fun ListTravels(userID: String, onNavigateHome:() -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    val selectedTravelId = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravels(Integer.parseInt(userID))
    val travels by viewModel.travels.collectAsState()


    LazyColumn() {
        items(items = travels.filter { selectedTravelId.value == null || it.id == selectedTravelId.value }) { travel ->
            val iconReason = when (travel.reason) {
                0 -> R.drawable.bussines
                else -> R.drawable.lazer
            }
            screen(travel, iconReason, selectedTravelId.value != null, viewModel, onCardClick = {
                selectedTravelId.value = it.id
            },
                onNavigateHome = { selectedTravelId.value = null })
        }
    }
}
