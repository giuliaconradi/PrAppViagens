package com.example.prappviagens.dao

import androidx.room.*
import com.example.prappviagens.entity.User
import com.example.prappviagens.entity.Viagem

@Dao
interface ViagemDao {

    @Insert
    suspend fun insert(viagem: Viagem)

    @Update
    suspend fun update(viagem: Viagem)

    @Query("UPDATE Viagem SET orcamento = :newOrcamento WHERE id = :id ")
    suspend fun incrementExpenses(id: Int, newOrcamento: Float)


    @Delete
    suspend fun delete(viagem: Viagem)

    @Query("select * from viagem t order by t.data_inicial")
    suspend fun findAll(): List<Viagem>

    @Query("select * from viagem t where t.userID = :userId order by t.data_inicial")
    suspend fun findAllByUserId(userId: Int): List<Viagem>

    @Query("select * from viagem t where t.destino = :destino")
    suspend fun findByDestino(destino: String): Viagem
}