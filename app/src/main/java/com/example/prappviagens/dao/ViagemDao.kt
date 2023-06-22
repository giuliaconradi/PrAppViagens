package com.example.prappviagens.dao

import androidx.room.*
import com.example.prappviagens.entity.DespesaViagem
import com.example.prappviagens.entity.Viagem

@Dao
interface ViagemDao {

    @Insert
    fun insert(viagem: Viagem)

    @Update
    fun update(viagem: Viagem)

    @Query("UPDATE Viagem SET orcamento = :newOrcamento WHERE id = :id ")
    fun incrementExpenses(id: Int, newOrcamento: Float)


    @Delete
    fun delete(viagem: Viagem)

    @Query("select * from viagem t order by t.data_inicial")
    fun findAll(): List<Viagem>

    @Query("select * from viagem t where t.userID = :userId order by t.data_inicial")
    fun findAllByUserId(userId: Int): List<Viagem>

    @Query("select * from viagem t where t.destino = :destino")
    fun findByDestino(destino: String): Viagem

    @Transaction
    @Query("SELECT t.*, e.*, SUM(e.valor) as valueExpense FROM viagem t INNER JOIN despesa e ON t.id = e.viagemID AND t.userID = :userId GROUP BY t.destino")
    fun findAllDatas(userId: Int): List<DespesaViagem>
}