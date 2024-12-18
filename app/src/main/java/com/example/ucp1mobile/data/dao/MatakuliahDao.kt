package com.example.ucp1mobile.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao {
    @Insert
    suspend fun insertMatakuliah(matakuliah: Matakuliah) //menggunakan suspend karena operasinya berat (insert, update, delete)

    //getAllMahasiswa
    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMatakuliah() : Flow<List<Matakuliah>>

    //getMahasiswa
    @Query("SELECT * FROM matakuliah WHERE nim = :nim")
    fun getMatakuliah(nim: String) : Flow<Matakuliah>

    //deleteMahasiswa
    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)

    //updateMahasiswa
    @Update
    suspend fun updateMatakuliah(matakuliah: Matakuliah)
}