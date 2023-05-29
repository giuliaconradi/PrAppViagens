package com.example.prappviagens.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prappviagens.entity.Viagem
import com.example.prappviagens.repository.ViagemRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterNewViagemViewModel(private val ViagemRepository: ViagemRepository): ViewModel() {

        var userID by mutableStateOf("")
        var destino by mutableStateOf("")
        var data_inicial by mutableStateOf("")
        var data_final by mutableStateOf("")
        var orcamento by mutableStateOf("")

        var isDestinoValid by mutableStateOf(true)

        private val _toastMessage = MutableSharedFlow<String>()
        val toastMessage = _toastMessage.asSharedFlow()

        private fun validateFields() {
            isDestinoValid = destino.isNotEmpty()
            if (!isDestinoValid) {
                throw Exception("Name is required")
            }
        }

        fun registrar() {
            val orcamentoFloat = orcamento.toFloatOrNull() ?: 0.0f
            try {
                validateFields()
                val newViagem = Viagem(userID = userID, destino = destino, data_inicial = data_inicial, data_final = data_final, orcamento = orcamentoFloat)
                ViagemRepository.addViagem(newViagem)
            } catch (e: Exception) {
                viewModelScope.launch {
                    _toastMessage.emit(e.message ?: "Unknown error")
                }
            }

        }
    }