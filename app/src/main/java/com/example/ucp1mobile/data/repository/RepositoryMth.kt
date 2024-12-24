package com.example.ucp1mobile.data.repository


import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow
//  RepositoryMth untuk pengelolaan data Matakuliah
interface RepositoryMth {
    suspend fun inssertMth(matakuliah: Matakuliah)
    fun getAllMth(): Flow<List<Matakuliah>>
    fun getMth(kode: String): Flow<Matakuliah>
    suspend fun deleteMth(matakuliah: Matakuliah)
    suspend fun updateMatakuliah(matakuliah: Matakuliah)
}