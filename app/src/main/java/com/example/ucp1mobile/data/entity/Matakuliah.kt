package com.example.ucp1mobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Matakuliah")
data class  Matakuliah(
    @PrimaryKey
    val kode: String,
    val nama: String,
    val sks: String,
    val semester: String,
    val jenis: String,
    val dosenPengampu: String,
)

