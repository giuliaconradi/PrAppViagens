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
      //  moreExpenses(vgm, viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          /**  Button(
                onClick = {
                    viewModel.updateExpenses(vgm.id, //calcularDespesa(vgm.orcamento, viewModel.orcamento))
                    //onNavigateMenuBar()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) { */
                Text("Atualizar")
            }
        }
    }

//}

@Composable
fun ListaDespesas(travelId: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewDespesaViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
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
            expensesPresent(despesa = it)
            println(it)

        }
    }
}

@Composable
fun expensesPresent(despesa: Despesa) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row {
            Text(
                text = despesa.descricao,
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
            Text(
                text = despesa.valor.toString(),
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
        }
    }
}


fun formatFloat(number: Float): String {
    return String.format("%.2f", number)
}

// Uso:


@Composable
fun eDespesa(
    descricao: String,
    viagem: DespesaViagem,
    viewModel: RegisterNewViagemViewModel,
    onDescriptionTextChanged: (String) -> Unit
) {
    var orcamento = viagem.valor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ListaDespesas(viagem.viagemID.toString())
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
            value = descricao,
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
           // telaViagem(travel, iconReason, selectedTravelId.value != null, viewModel, onCardClick = {
            //    selectedTravelId.value = it.id
         //   }, //onNavigateHome = { selectedTravelId.value = null })
        }
    }
}
