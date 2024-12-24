package com.example.ucp1mobile.data.repository

import com.example.ucp1mobile.data.dao.MatakuliahDao
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow
//implementasi local repository untuk mengelola data Matakuliah
class LocalRepositoryMth(
    private val matakuliahDao: MatakuliahDao
) : RepositoryMth {
    override suspend fun inssertMth(matakuliah: Matakuliah) {
        matakuliahDao.insertMatakuliah(matakuliah)
    }

    override fun getAllMth(): Flow<List<Matakuliah>> {
        return matakuliahDao.getAllMatakuliah()
    }

    override fun getMth(kode: String): Flow<Matakuliah> {
        return matakuliahDao.getMth(kode)  // Sesuaikan dengan nama di DAO
    }

    override suspend fun deleteMth(matakuliah: Matakuliah) {
        matakuliahDao.deleteMatakuliah(matakuliah)
    }

    override suspend fun updateMatakuliah(matakuliah: Matakuliah) {
        matakuliahDao.updateMatakuliah(matakuliah)
    }
}