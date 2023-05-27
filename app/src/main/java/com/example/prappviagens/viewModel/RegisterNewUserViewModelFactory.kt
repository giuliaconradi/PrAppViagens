package com.example.prappviagens.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prappviagens.database.AppDatabase
import com.example.prappviagens.repository.UserRepository

class RegisterNewUserViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getDatabase(application).userDao()
        val userRepository = UserRepository(dao)
        return RegisterNewUserViewModel(userRepository) as T
    }
}
