package com.example.ucp1mobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
//Tabel matakuliah
@Entity(tableName = "matakuliah")
data class Matakuliah(
    @PrimaryKey
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)