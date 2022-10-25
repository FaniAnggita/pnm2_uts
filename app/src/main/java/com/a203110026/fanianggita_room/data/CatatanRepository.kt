package com.a203110026.fanianggita_room.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO 4: Membuat kelas Repositori
/*
Kelas repositori pada dasarnya mengabstraksi akses ke berbagai sumber data seperti mendapatkan data dari API
atau mendapatkan data dari database Room.
 */
class CatatanRepository @Inject constructor(private val catatanDAO: CatatanDAO) {

    // Membuat variabel 'notes' untuk mendapatkan semua data dari kelas catatanDAO.
    val notes = catatanDAO.getSemuaCatatan()

    suspend fun getCatatan(id: Int) = withContext(Dispatchers.IO) {
        catatanDAO.getCatatanSpesifik(id)
    }

    // Method insert untuk menambah catatan
    suspend fun insertCatatanBaru(note: CatatanEntitas) = withContext(Dispatchers.IO) {
        catatanDAO.insertCatatan(note)
    }

    // Method update untuk memperbarui catatan
    suspend fun PerbaruiCttn(note: CatatanEntitas) = withContext(Dispatchers.IO) {
        catatanDAO.PerbaruiCttn(note)
    }
}