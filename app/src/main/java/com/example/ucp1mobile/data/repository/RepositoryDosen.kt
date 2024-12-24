package com.example.ucp1mobile.data.repository

import com.example.ucp1mobile.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    suspend fun insertDosen(dosen: Dosen)

    fun getAllDosen(): Flow<List<Dosen>>

    fun getDosen(nidn: String): Flow<Dosen>

    suspend fun deleteDosen(dosen: Dosen)

    suspend fun updateDosen(dosen: Dosen)
}