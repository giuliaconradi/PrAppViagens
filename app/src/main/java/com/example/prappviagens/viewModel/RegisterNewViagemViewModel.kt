package com.example.prappviagens.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prappviagens.entity.DespesaViagem
import com.example.prappviagens.entity.Viagem
import com.example.prappviagens.repository.ViagemRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterNewViagemViewModel(private val ViagemRepository: ViagemRepository): ViewModel() {

        var userID by mutableStateOf("")
        var destino by mutableStateOf("")
        var data_inicial by mutableStateOf("")
        var data_final by mutableStateOf("")
        var orcamento by mutableStateOf(0f)
        var reason by mutableStateOf(0)

        var isDestinoValid by mutableStateOf(true)

        private val _toastMessage = MutableSharedFlow<String>()
        val toastMessage = _toastMessage.asSharedFlow()

        private fun validateFields() {
            isDestinoValid = destino.isNotEmpty()
            if (!isDestinoValid) {
                throw Exception("Name is required")
            }
        }

         fun updateExpenses(id: Int, orcamento: Float){
             ViagemRepository.attATravel(id, orcamento)
         }

        fun registrar(usuario: Int) {
            val orcamentoFloat = orcamento
            try {
                validateFields()
                val newViagem = Viagem(userID = usuario.toString(), destino = destino, data_inicial = data_inicial, data_final = data_final, orcamento = orcamentoFloat, reason = reason)
                ViagemRepository.addViagem(newViagem)
            } catch (e: Exception) {
                viewModelScope.launch {
                    _toastMessage.emit(e.message ?: "Unknown error")
                }
            }

        }
    val viagem: MutableStateFlow<List<Viagem>> = MutableStateFlow(emptyList())
    val DespesaViagem: MutableStateFlow<List<DespesaViagem>> = MutableStateFlow(emptyList())

    fun getTravels(userId: Int) {
        viewModelScope.launch {
            val travelsRepo = ViagemRepository.getAllTravels(userId)
            viagem.value = travelsRepo
        }
    }
    fun getTravelsWithExpenses(userId: Int) {
        viewModelScope.launch {
            val travelsRepo = ViagemRepository.getAllThinks(userId)
            DespesaViagem.value = travelsRepo
        }
    }


    fun getTravelByName(destino: String) = runBlocking {
        ViagemRepository.getTravelByName(destino)
    }


}