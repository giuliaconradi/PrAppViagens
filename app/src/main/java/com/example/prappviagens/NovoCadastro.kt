package com.example.prappviagens.ui.theme

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NovoCadastro(onBackNavigate: () -> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Novo Cadastro! :)",
            style = MaterialTheme.typography.h5,
            color = Color(0xFF03A9F4) // Cor desejada
        )
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField( //campo usuário
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Usuário") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField( //campo senha
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )
        OutlinedTextField( //campo e-mail
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Endereço de e-mail") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button( //botão cadastrar
            onClick = {
                viewModel.registrar(onBackNavigate)
            },
            modifier = Modifier
                //.fillMaxWidth()
                .width(280.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4)),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "CADASTRAR")
        }
    }
}