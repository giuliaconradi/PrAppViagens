package com.example.prappviagens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NovoCadastro() {
    val application = LocalContext.current.applicationContext as Application
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val viewModel: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cadastro de Usuário")
                },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = { Text("Usuário") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    label = { Text("Confirmar Senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                       viewModel.registrar()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Cadastrar-se")
                }
            }
        }
    )
}
