package com.a203110026.fanianggita_room.data

import androidx.room.Database
import androidx.room.RoomDatabase

// TODO 3: Mengimplementasikan database Room pada aplikasi
/*
Untuk menggunakan Room, kita membutuhkan instance dari kelas RoomDatabase.
Maka, kita perlu membuat subclass abstrak dari class RoomDatabase.
 */
@Database(entities = [CatatanEntitas::class], version = 1, exportSchema = false)
// kelas ini untuk Room harus abstract dan extend RoomDatabase.
abstract class CatatanDatabase : RoomDatabase() {
    abstract fun noteDao(): CatatanDAO
}
