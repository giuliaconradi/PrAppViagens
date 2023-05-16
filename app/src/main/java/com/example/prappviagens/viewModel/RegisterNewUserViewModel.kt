package com.example.prappviagens.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prappviagens.entity.User
import com.example.prappviagens.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class RegisterNewUserViewModel(private val userRepository: UserRepository): ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isNameValid by mutableStateOf(true)

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun validateFields() {
        isNameValid = name.isNotEmpty()
        if (!isNameValid) {
            throw Exception("Name is required")
        }
    }

    fun registrar(onSuccess: () -> Unit) {
        try {
            validateFields()
            val newUser = User(name = name, email = email, password = password)
            userRepository.addUser(newUser)
            onSuccess()
        }
        catch (e: Exception) {
            viewModelScope.launch {
                _toastMessage.emit(e.message?: "Unknown error")
            }
        }

    }

}