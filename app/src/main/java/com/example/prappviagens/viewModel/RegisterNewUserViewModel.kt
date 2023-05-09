package com.example.prappviagens.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.prappviagens.entity.User
import com.example.prappviagens.repository.UserRepository


class RegisterNewUserViewModel(private val userRepository: UserRepository): ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun registrar() {
        val newUser = User(name = name, email = email, password = password)
        userRepository.addUser(newUser)
    }
}