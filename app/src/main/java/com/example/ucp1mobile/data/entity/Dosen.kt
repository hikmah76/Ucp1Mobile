package com.example.ucp1mobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
//Tabel Dosen
@Entity(tableName = "dosen")
data class Dosen(
    @PrimaryKey
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)

