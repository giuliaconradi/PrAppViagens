package com.example.prappviagens.repository

import com.example.prappviagens.dao.ViagemDao
import com.example.prappviagens.entity.Viagem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViagemRepository (private val ViagemDao: ViagemDao) {


    private val coroutine = CoroutineScope(Dispatchers.Main)


    fun addViagem(viagem: Viagem) {
        coroutine.launch(Dispatchers.IO) {
            ViagemDao.insert(viagem)
        }
    }

    fun delete(viagem: Viagem) {
        coroutine.launch(Dispatchers.IO) {
            ViagemDao.delete(viagem)
        }
    }

    class TravelRepository(private val travelDao: ViagemDao) {
        private val coroutine = CoroutineScope(Dispatchers.Main)

        fun addTravel(travel: Viagem) {
            coroutine.launch(Dispatchers.IO) {
                travelDao.insert(travel)
            }
        }

    }
    suspend fun getAllTravels(userId: Int): List<Viagem> {
        return withContext(Dispatchers.IO) {
            ViagemDao.findAllByUserId(userId)
        }
    }
    fun attATravel(id: Int,  orcamento: Float){
        coroutine.launch(Dispatchers.IO){
            ViagemDao.incrementExpenses(id,orcamento)
        }
    }
}

