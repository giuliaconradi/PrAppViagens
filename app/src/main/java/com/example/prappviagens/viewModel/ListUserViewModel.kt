package com.example.prappviagens.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prappviagens.entity.User
import com.example.prappviagens.repository.UserRepository
import kotlinx.coroutines.launch


class ListUserViewModel(private val userRepository: UserRepository): ViewModel() {

    var users: MutableState<List<User>> = mutableStateOf(listOf())

    var showDialogDelete = mutableStateOf(false)

    var userForDelete: User? by mutableStateOf(null)

    fun loadAllUsers(){
        viewModelScope.launch {
            users.value = userRepository.loadAllUsers()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userForDelete?.let {
                userRepository.delete(it)
                loadAllUsers()
            }
        }
    }








}