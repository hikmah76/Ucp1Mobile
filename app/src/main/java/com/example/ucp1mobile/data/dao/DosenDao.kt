package com.example.ucp1mobile.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp1mobile.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao // Berfungsi untuk mengakses, menyimpan, memperbarui, dan menghapus data Dosen di Room Database.
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>

    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>

    @Delete
    suspend fun deleteDosen(dosen: Dosen)

    @Update
    suspend fun updateDosen(dosen: Dosen)
}