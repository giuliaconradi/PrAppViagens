package com.example.prappviagens.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prappviagens.entity.Despesa

@Dao
interface DespesaDao {
    @Insert
    suspend fun insert(despesa: Despesa)

    @Update
    suspend fun update(despesa: Despesa)

    @Query("UPDATE despesa SET valor = :value WHERE DespesaID = :id ")
    suspend fun incrementaDespesa(id: Int, value: Float)

    @Delete
    suspend fun delete(expense: Despesa)

    @Query("select * from despesa e order by e.valor")
    suspend fun findAll(): List<Despesa>

    @Query("select * from despesa e where e.viagemID =:travelId order by e.valor")
    suspend fun findAllByTravel(travelId: Int): List<Despesa>
}