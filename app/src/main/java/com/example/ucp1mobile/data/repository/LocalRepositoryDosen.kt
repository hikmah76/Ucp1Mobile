package com.example.ucp1mobile.data.repository

import com.example.ucp1mobile.data.dao.DosenDao
import com.example.ucp1mobile.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen( // Implementasi local repository untuk mengelola data dosen
    private val dosenDao: DosenDao
) : RepositoryDosen {
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }

    override suspend fun deleteDosen(dosen: Dosen) {
        dosenDao.deleteDosen(dosen)
    }

    override suspend fun updateDosen(dosen: Dosen) {
        dosenDao.updateDosen(dosen)
    }
}