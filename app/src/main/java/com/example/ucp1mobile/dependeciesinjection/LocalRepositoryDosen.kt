package com.example.ucp1mobile.dependeciesinjection

import com.example.ucp1mobile.data.dao.MatakuliahDao
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen (
    private val mahasiswaDao: MatakuliahDao
) : RepositoryDosen {
    override suspend fun inssertMhs(dosen: Dosen) {
        mahasiswaDao.insertMahasiswa(dosen)
    }

    suspend fun insertMhs(dosen: Dosen) {
        mahasiswaDao.insertMahasiswa(dosen)
    }

    //getAllMhs
    override fun getAllMhs(): Flow<List<Dosen>> {
        return mahasiswaDao.getAllMahasiswa()
    }

    //getMhs
    override fun getMhs(nim: String): Flow<Dosen> {
        return mahasiswaDao.getMahasiswa(nim)
    }

    //deleteMhs
    override suspend fun deleteMhs(mahasiswa: Matakuliah) {
        mahasiswaDao.deleteMahasiswa(mahasiswa)
    }

    //updateMhs
    override suspend fun updateMhs(matakuliah: Matakuliah) {
        mahasiswaDao.updateMahasiswa(matakuliah)
    }


}