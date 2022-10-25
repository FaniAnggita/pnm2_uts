package com.a203110026.fanianggita_room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// TODO 1: Membuat kelas entitas untuk membuat struktur database

// Menentukan nama tabel
@Entity(tableName = "Notes")
data class CatatanEntitas(

    // Menentukan daftar kolom dan primary key
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var judulcatatan: String? = null,

    @ColumnInfo(name = "date_time")
    var dateTime: String? = null,

    @ColumnInfo(name = "note_text")
    var desCatatan: String? = null,

    @ColumnInfo(name = "img_path")
    var gambar: String? = null,

    @ColumnInfo(name = "web_link")
    var linkgambar: String? = null,

    @ColumnInfo(name = "color")
    var color: String? = null,

):Serializable{
    // Menentukan ID data berdasarkan tanggal simpan data
    override fun toString(): String {
        return "$judulcatatan : $dateTime"
    }
}
