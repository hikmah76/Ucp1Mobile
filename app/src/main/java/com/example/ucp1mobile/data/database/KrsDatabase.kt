package com.example.ucp1mobile.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp1mobile.data.dao.DosenDao
import com.example.ucp1mobile.data.dao.MatakuliahDao
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.entity.Matakuliah

@Database( // database lokal Room untuk menyimpan data Dosen dan Matakuliah
    entities = [Dosen::class, Matakuliah::class],
    version = 1,
    exportSchema = false
)
abstract class KrsDatabase : RoomDatabase() {
    abstract fun dosenDao(): DosenDao
    abstract fun matakuliahDao(): MatakuliahDao

    companion object {
        private const val DATABASE_NAME = "krs_database"

        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Gunakan applicationContext
                    KrsDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = instance
                instance
            }
        }
    }
}