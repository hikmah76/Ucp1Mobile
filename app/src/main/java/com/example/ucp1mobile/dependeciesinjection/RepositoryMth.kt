package com.example.ucp1mobile.dependeciesinjection


import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMth {
    suspend fun inssertMhs(matakuliah: Matakuliah)
    //getAllMhs
    fun getAllMhs(): Flow<List<Matakuliah>>

    //getMhs
    fun getMhs(nim: String): Flow<Matakuliah>

    //deleteMhs
    suspend fun deleteMhs(matakuliah: Matakuliah)

    //updateMhs
    suspend fun updateMhs(matakuliah: Matakuliah)


}