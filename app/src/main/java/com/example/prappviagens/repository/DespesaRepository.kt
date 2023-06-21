package com.example.prappviagens.repository

import com.example.prappviagens.dao.DespesaDao
import com.example.prappviagens.entity.Despesa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DespesaRepository (private val despesaDao: DespesaDao) {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    suspend fun insertDespesa(despesa: Despesa){
        despesaDao.insert(despesa)
    }

    fun updateDespesa(id: Int,  orcamento: Float){
        coroutine.launch(Dispatchers.IO){
            despesaDao.incrementaDespesa(id,orcamento)
        }
    }

    suspend fun getDespesa(viagemID:Int):List<Despesa> {
        return despesaDao.findAllByTravel(viagemID)
    }

}