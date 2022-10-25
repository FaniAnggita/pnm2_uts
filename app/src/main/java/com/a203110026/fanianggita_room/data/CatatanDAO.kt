package com.a203110026.fanianggita_room.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// TODO 2: Membuat kelas DAO untuk menentukan kueri SQL

// @Dao untuk menganotasikan bahwa ini adalah kelas DAO untuk room
@Dao
interface CatatanDAO {

    // Method untuk mengembalikan kueri pengambalian semua data dari tabel "notes" dengan urutan id turun (turun).
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getSemuaCatatan(): Flow<List<CatatanEntitas>>

    // Method untuk mengembalikan kueri pengambalian pencarian spesifik.
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getCatatanSpesifik(id: Int): CatatanEntitas

    // Method untuk menangani insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatatan(note: CatatanEntitas)

    // Method untuk menangani delete semua data
    @Delete
    suspend fun deleteCatatan(note: CatatanEntitas)

    // Method untuk menangani delete data tertentu
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteCatatanSpesifik(id: Int)

    // Method untuk menangani update data
    @Update
    suspend fun PerbaruiCttn(note: CatatanEntitas)
}
