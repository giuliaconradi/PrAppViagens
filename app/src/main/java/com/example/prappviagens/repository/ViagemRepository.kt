package com.example.prappviagens.repository

import com.example.prappviagens.dao.ViagemDao
import com.example.prappviagens.entity.Viagem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    suspend fun loadAllViagem(): List<Viagem> {
        return ViagemDao.getAll()
    }
    suspend fun findByViagem(viagem: String): Viagem? =
        ViagemDao.findbyViagem(viagem)
}