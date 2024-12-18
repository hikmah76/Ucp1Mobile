package com.example.ucp1mobile.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp1mobile.data.dao.DosenDao
import com.example.ucp1mobile.data.dao.MatakuliahDao
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.entity.Matakuliah


//Mendefinisikan database dengan tabel Mahasiswa

@Database(entities = [Dosen::class, Matakuliah::class], version = 2, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    //Mendefinisikan fungsi untuk mengakses data Mahasiswa
    abstract fun dosenDao(): DosenDao
    abstract fun matakuliahDao(): MatakuliahDao

    companion object {
        @Volatile //Memastikan bahwa nilai variabel Instance selalu sama di
        private var Instance: KrsDatabase? = null

        // template
        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java, //Class database
                    "KrsDatabase" // Nama database
                )

                    .build().also { Instance = it }
            })
        }
    }
}
