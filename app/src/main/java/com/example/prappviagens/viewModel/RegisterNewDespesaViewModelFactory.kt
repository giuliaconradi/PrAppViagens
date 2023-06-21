package com.example.prappviagens.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prappviagens.database.AppDatabase
import com.example.prappviagens.repository.DespesaRepository

class RegisterNewDespesaViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getDatabase(application).DespesaDao()
        val expenseRepository = DespesaRepository(dao)
        return RegisterNewDespesaViewModel(expenseRepository) as T
    }
}