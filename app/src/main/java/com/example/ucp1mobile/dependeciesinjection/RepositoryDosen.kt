package com.example.ucp1mobile.dependeciesinjection

import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    suspend fun inssertMhs(dosen: Dosen)
    //getAllMhs
    fun getAllMhs(): Flow<List<Dosen>>

    //getMhs
    fun getMhs(nim: String): Flow<Dosen>

    //deleteMhs
    suspend fun deleteMhs(dosen: Dosen)

    //updateMhs
    suspend fun updateMhs(dosen: Dosen)


}