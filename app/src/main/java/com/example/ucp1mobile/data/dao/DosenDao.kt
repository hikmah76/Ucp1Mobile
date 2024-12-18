package com.example.ucp1mobile.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp1mobile.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface DosenDao {
    @Insert
    suspend fun insertMahasiswa(dosen: Dosen) //menggunakan suspend karena operasinya berat (insert, update, delete)

    //getAllMahasiswa
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen() : Flow<List<Dosen>>

    //getMahasiswa
    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nim: String) : Flow<Dosen>

    //deleteDosen
    @Delete
    suspend fun deleteDosen(dosen: Dosen)

    //updateMahasiswa
    @Update
    suspend fun updateDosen(dosen: Dosen)
}