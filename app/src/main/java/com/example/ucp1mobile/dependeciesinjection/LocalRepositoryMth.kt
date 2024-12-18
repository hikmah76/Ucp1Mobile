package com.example.ucp1mobile.dependeciesinjection


import com.example.ucp1mobile.data.dao.MatakuliahDao
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMth (
    private val matakuliahDao: MatakuliahDao
) : RepositoryMth {
    override suspend fun inssertMhs(mahasiswa: Matakuliah) {
        matakuliahDao.insertMahasiswa(mahasiswa)
    }

    suspend fun insertMhs(matakuliah: Matakuliah) {
        matakuliahDao.insertMahasiswa(matakuliah)
    }

    //getAllMhs
    override fun getAllMhs(): Flow<List<Matakuliah>> {
        return matakuliahDao.getAllMahasiswa()
    }

    //getMhs
    override fun getMhs(nim: String): Flow<Matakuliah> {
        return matakuliahDao.getMahasiswa(nim)
    }

    //deleteMhs
    override suspend fun deleteMhs(mahasiswa: Matakuliah) {
        matakuliahDao.deleteMahasiswa(mahasiswa)
    }

    //updateMhs
    override suspend fun updateMhs(matakuliah: Matakuliah) {
        matakuliah.updateMahasiswa(matakuliah)
    }


}