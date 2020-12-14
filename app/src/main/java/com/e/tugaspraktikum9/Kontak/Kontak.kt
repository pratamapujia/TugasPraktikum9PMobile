package com.e.tugaspraktikum9.Kontak

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kontak_table")
data class Kontak(
    var Nama: String,
    var Description: String,
    var Phone: String,
    var Priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}