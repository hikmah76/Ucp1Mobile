package com.example.ucp1mobile.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp1mobile.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao //Berfungsi untuk mengakses, menyimpan, memperbarui, dan menghapus data Matakuliah di Room Database
interface MatakuliahDao {
    @Insert
    suspend fun insertMatakuliah(matakuliah: Matakuliah)

    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMth(kode: String): Flow<Matakuliah>  // Diganti jadi getMth

    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)

    @Update
    suspend fun updateMatakuliah(matakuliah: Matakuliah)
}