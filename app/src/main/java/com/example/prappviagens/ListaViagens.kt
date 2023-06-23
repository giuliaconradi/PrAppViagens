package com.example.prappviagens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.entity.Despesa
import com.example.prappviagens.entity.DespesaViagem
import com.example.prappviagens.viewModel.RegisterNewDespesaViewModel
import com.example.prappviagens.viewModel.RegisterNewDespesaViewModelFactory
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
    onNavigateMenuBar: () -> Unit,
) {
    val application = LocalContext.current.applicationContext as Application
    val expenseViewModel: RegisterNewDespesaViewModel = viewModel(
        factory = RegisterNewDespesaViewModelFactory(application)
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
        backgroundColor = Color(0xFFB3E5FC)
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
                        style = MaterialTheme.typography.subtitle1,
                        color = Color(0xFFFF4081)
                    )
                    Text(
                        text = "Partida: ${viagem.data_inicial} Chegada: ${viagem.data_final}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Total: ${formatFloat(viagem.valor)}",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3E5F5)) // Defina a cor de fundo desejada aqui (rosa bebê)
                .padding(horizontal = 16.dp),
        ) {}
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val id = viagem.viagemID
                        expenseViewModel.vDespesa(
                            id, descriptionText, viewModel.orcamento
                        )
                        descriptionText = ""
                        viewModel.getTravelsWithExpenses(viagem.userID)
                        onNavigateMenuBar()
                    }
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
                    .align(Alignment.CenterHorizontally),
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
                    .border(3.dp, Color(0xFF03A9F4))
                    .padding(4.dp)
                    .background(Color(0xFFB3E5FC))
            )
            Text(
                text = expense.valor.toString(),
                modifier = Modifier
                    .weight(1.0f)
                    .border(3.dp, Color(0xFF03A9F4))
                    .padding(4.dp)
                    .background(Color(0xFFB3E5FC))
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
fun ListaViagens(userID: String, onNavigateMenuBar: () -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    val pViagemID = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravelsWithExpenses(Integer.parseInt(userID))
    val viagem by viewModel.DespesaViagem.collectAsState()


    LazyColumn() {
        items(items = viagem.filter { pViagemID.value == null || it.id == pViagemID.value }) { viagem ->
            val iconReason = when (viagem.reason) {
                0 -> R.drawable.lazer
                else -> R.drawable.trabalho
            }
            screen(viagem, iconReason, pViagemID.value != null, viewModel, onCardClick = {
                pViagemID.value = it.id
            }, onNavigateMenuBar = { pViagemID.value = null })
        }
    }
}
