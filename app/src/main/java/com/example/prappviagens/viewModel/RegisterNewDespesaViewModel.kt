package com.example.prappviagens.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prappviagens.entity.Despesa
import com.example.prappviagens.repository.DespesaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterNewDespesaViewModel (private val despesaRepository: DespesaRepository): ViewModel() {
    var descricao by mutableStateOf("")
    var data_inicial by mutableStateOf("")
    var data_final by mutableStateOf("")
    var valor by  mutableStateOf(0f)
    var reason by  mutableStateOf(0)


    suspend fun registrar(viagem: Int){
        val despesa = Despesa(DespesaID = 0, viagemID = viagem, descricao = descricao, valor = valor )
        return despesaRepository.insertDespesa(despesa)

    }

    suspend fun valorDespesa(viagem: Int,desc:String, value: Float){
        val despesa = Despesa(DespesaID = 0, viagemID = viagem, descricao = desc, valor = value)
        return despesaRepository.insertDespesa(despesa)

    }

    val despesaViagem: MutableStateFlow<List<Despesa>> = MutableStateFlow(emptyList())


    fun getDespesa(viagemID:Int){
        viewModelScope.launch{
            val despesa = despesaRepository.getDespesa(viagemID)
            despesaViagem.value = despesa
        }
    }

    fun updateDespesa(id: Int, orcamento: Float){
        despesaRepository.updateDespesa(id, orcamento)
    }
}