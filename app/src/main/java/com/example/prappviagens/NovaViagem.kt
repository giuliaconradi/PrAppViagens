package com.example.prappviagens

import android.annotation.SuppressLint
import android.app.Application
import android.widget.DatePicker
import androidx.compose.foundation.Image
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModel
import com.example.prappviagens.viewModel.RegisterNewViagemViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
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
    val ano: Int
    val mes: Int
    val dia: Int
    val calendario = Calendar.getInstance()

    ano = calendario.get(Calendar.YEAR)
    mes = calendario.get(Calendar.MONTH)
    dia = calendario.get(Calendar.DAY_OF_MONTH)

    calendario.time = Date()

    val mDatePickerDialogStart= android.app.DatePickerDialog(
        application,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.data_inicial = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, ano, mes, dia
    )
    val mDatePickerDialogEnd = android.app.DatePickerDialog(
        application,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.data_final = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, ano, mes, dia
    )
    fun openDatePicker(focused: Boolean, date: String) {
        if (focused && date == "Data Inicial") {
            mDatePickerDialogStart.show();
        }
        if (focused && date == "Data Final"){
            mDatePickerDialogEnd.show()
        }
    }

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
            }
            OutlinedTextField(
                value = viewModel.data_inicial,
                onValueChange = {},
                label = {
                    Text(text = "Start Date")
                },
                modifier = Modifier.onFocusChanged { a -> openDatePicker(a.isFocused, "Data Inicial") }
            )
            OutlinedTextField(
                value = viewModel.data_final,
                onValueChange = {},
                label = {
                    Text(text = "End Date")
                },
                modifier = Modifier.onFocusChanged { b -> openDatePicker(b.isFocused, "Data Final") }
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.lazer),
                    contentDescription = "Ícone do Lazer",
                    modifier = Modifier.size(64.dp)
                )
                RadioButton(
                    selected = selectedOption == 0,
                    onClick = { selectedOption = 0 },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("Lazer", Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.trabalho),
                contentDescription = "Ícone de Negócios",
                modifier = Modifier.size(64.dp)
            )
            RadioButton(
                selected = selectedOption == 1,
                onClick = { selectedOption = 1 },
                modifier = Modifier.padding(start = 8.dp)
            )
            Text("Negócios", Modifier.padding(start = 8.dp))
        }
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

fun checkFields(viewModel: RegisterNewViagemViewModel): Boolean {
    return viewModel.orcamento != null &&
            viewModel.destino?.isNotEmpty() == true &&
            viewModel.data_inicial?.isNotEmpty() == true &&
            viewModel.data_final?.isNotEmpty() == true
}

