package com.example.prappviagens
import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.LoginViewModel
import com.example.prappviagens.viewModel.LoginViewModelFactory
import com.example.prappviagens.viewModel.RegisterNewUserViewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory

@Composable
fun Login(onNavigateNovoCadastro: () -> Unit,
          onNavigateListaViagens: () -> Unit,
          onNavigateNovaViagem: (id: Int) -> Unit,
          onNavigateMenuBar: (id: Int) -> Unit
) {
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
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Usuário") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val isValidUser = viewModel.UsuarioExiste()
                if (isValidUser) {
                    onNavigateMenuBar.invoke(viewModel.userTravel(viewModel.name))
                } else {
                    println("erro <<<<<<<<<<<<<<<<<<<<<<<<<<<<")
                }
            },
            modifier = Modifier
                //.fillMaxWidth()
                .width(280.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4)),
            contentPadding = PaddingValues(16.dp)

        ) {
            Text(text = "LOGIN")
        }
        TextButton(
            onClick = { onNavigateNovoCadastro() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Transparent)
        ) {
            Text(text = "Criar novo usuário")
        }
    }
}