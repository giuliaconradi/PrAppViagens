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
import com.example.prappviagens.viewModel.RegisterNewDespesaViewModelFactory
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory
import com.example.prappviagens.viewModel.RegisterNewViagemViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun screen(
    viagem: DespesaViagem,
    iconReason: Int,
    selecao: Boolean,
    viewModel: RegisterNewViagemViewModel,
    onCardClick: (DespesaViagem) -> Unit,
    onNavigateHome: () -> Unit,
) {
    val application = LocalContext.current.applicationContext as Application
    val expenseViewModel: RegisterNewDespesaViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )
    var id by remember { mutableStateOf(0) }

    var descriptionText by remember {
        mutableStateOf("")
    }
    fun formatFloat(number: Float): String {
        return String.format("%.2f", number)
    }

    val scope = rememberCoroutineScope()
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
                        text = viagem.destino, style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${viagem.data_inicial} --> ${viagem.data_final}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${formatFloat(viagem.valor)}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
    if (selecao) {
        moreExpenses(
            descriptionText = descriptionText,
            travels = viagem,
            viewModel = viewModel,
            onDescriptionTextChanged = {
                descriptionText = it
            })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val id = viagem.viagemID
                        expenseViewModel.registrar(
                            id, descriptionText, viewModel.orcamento
                        )
                        descriptionText = ""
                        viewModel.getTravelsWithExpenses(viagem.userID)
                        onNavigateHome()
                    }
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text("Atualizar")
            }
        }
    }
}

@Composable
fun ListExpenses(travelId: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewDespesaViewModel = viewModel(
        factory = RegisterNewDespesaViewModelFactory(application)
    )
    viewModel.getDespesa(Integer.parseInt(travelId))
    val expenses by viewModel.despesaViagem.collectAsState()
    println(expenses)
    LazyColumn(
        modifier = Modifier
            .height(150.dp)
            .width(1000.dp)
    ) {
        items(items = expenses) {
            expensesPresent(expense = it)
            println(it)

        }
    }
}

@Composable
fun expensesPresent(expense: Despesa) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row {
            Text(
                text = expense.descricao,
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
            Text(
                text = expense.valor.toString(),
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun moreExpenses(
    descriptionText: String,
    travels: DespesaViagem,
    viewModel: RegisterNewViagemViewModel,
    onDescriptionTextChanged: (String) -> Unit
) {
    var orcamento = travels.valor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ListExpenses(travels.viagemID.toString())
        OutlinedTextField(
            value = orcamento.toString(),
            onValueChange = { orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Despesa atual") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {

            }),
            modifier = Modifier.padding(16.dp),
            enabled = false
        )
        OutlinedTextField(
            value = viewModel.orcamento.toString(),
            onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Nova Despesa") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {

            }),
            modifier = Modifier.padding(6.dp)
        ).toString()
        OutlinedTextField(
            value = descriptionText,
            onValueChange = {
                onDescriptionTextChanged(it)

//                test = it
            },
            label = { Text("Descrição") },
            enabled = true
        )

    }

}

@Composable
fun ListTravels(userID: String, onNavigateHome: () -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    val selectedTravelId = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravelsWithExpenses(Integer.parseInt(userID))
    val travels by viewModel.DespesaViagem.collectAsState()


    LazyColumn() {
        items(items = travels.filter { selectedTravelId.value == null || it.id == selectedTravelId.value }) { travel ->
            val iconReason = when (travel.reason) {
                0 -> R.drawable.trabalho
                else -> R.drawable.lazer
            }
            screen(travel, iconReason, selectedTravelId.value != null, viewModel, onCardClick = {
                selectedTravelId.value = it.id
            }, onNavigateHome = { selectedTravelId.value = null })
        }
    }
}
