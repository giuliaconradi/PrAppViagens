package com.example.prappviagens.dao

import androidx.room.*
import com.example.prappviagens.entity.User
import com.example.prappviagens.entity.Viagem

@Dao
    interface ViagemDao {

        @Insert
        fun insert(viagem: Viagem)

        @Update
        suspend fun update(viagem: Viagem)

        @Delete
        suspend fun delete(viagem: Viagem)

        @Query("select * from viagem v order by v.destino")
        suspend fun getAll(): List<Viagem>

        @Query("select * from viagem v where v.destino = :viagem")
        suspend fun findbyViagem(viagem: String): Viagem?



    }