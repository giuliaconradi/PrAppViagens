package com.example.prappviagens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NovaViagem(onNavigateMenuBar: () -> Unit, userID: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewViagemViewModel = viewModel(
        factory = RegisterNewViagemViewModelFactory(application)
    )

    var selectedOption by remember { mutableStateOf(0) }
    Scaffold(topBar =
    { TopAppBar(title = { Text(text = "Nova Viagem! :)") }, navigationIcon = { }) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.destino,
                onValueChange = { viewModel.destino = it },
                label = { Text("Destino") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, // espaço igual entre os elementos
                modifier = Modifier.fillMaxWidth() // preenche a largura máxima disponível
            ) {
                dateButtons(viewModel)
            }
            OutlinedTextField(
                value = viewModel.data_inicial,
                onValueChange = { viewModel.data_inicial = it },
                label = { Text("Data Inicio") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = viewModel.data_final,
                onValueChange = { viewModel.data_final = it },
                label = { Text("Data Final") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = viewModel.orcamento.toString(),
                onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
                label = { Text("Orçamento") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { /* Ação para avançar para o próximo campo */ }
                ),
                modifier = Modifier.padding(16.dp)
            ).toString()
            Row {
                RadioButton(
                    selected = selectedOption == 0,
                    onClick = { selectedOption = 0 },
                )
                Text("Lazer", Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 },
                )
                Text("Negócios", Modifier.padding(start = 8.dp))

            }

            Button(
                onClick = {
                    if (checkFields(viewModel)) {
                        viewModel.registrar(Integer.parseInt(userID))
                        println()
                        onNavigateMenuBar()

                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "OK"
                )
            }
        }
    }
}

@Composable
fun dateButtons(viewModel: RegisterNewViagemViewModel){
    var showDialogStart by remember { mutableStateOf(false) }
    var showDialogEnd by remember { mutableStateOf(false) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }
    Column {
        Button(
            onClick = { showDialogStart = true },
            modifier = Modifier
                .height(40.dp)
        ) {
            Text("Data de entrada")
        }

        DatePickerDialog(showDialogStart, { showDialogStart = false }) { newDate ->
            startDate = newDate
            showDialogStart = false
        }
        viewModel.data_inicial = dateFormatter.format(startDate)
    }

    Column {
        Button(
            onClick = { showDialogEnd = true },
            modifier = Modifier
                .height(40.dp)
        ) {
            Text("Data de Saida")
        }

        DatePickerDialog(showDialogEnd, { showDialogEnd = false }) { newDate ->
            endDate = newDate
            showDialogEnd = false
        }
        viewModel.data_final = dateFormatter.format(endDate)
        print(dateFormatter.format(endDate))
    }
}

fun checkFields(viewModel: RegisterNewViagemViewModel): Boolean {
    return viewModel.orcamento != null &&
            viewModel.destino?.isNotEmpty() == true &&
            viewModel.data_inicial?.isNotEmpty() == true &&
            viewModel.data_final?.isNotEmpty() == true
}


@Composable
fun DatePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSet: (Date) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            android.app.DatePickerDialog(LocalContext.current, { _, year, month, dayOfMonth ->
                onDateSet(GregorianCalendar(year, month, dayOfMonth).time)
            }, 2023, 6, 12).show()
        }
    }
}
