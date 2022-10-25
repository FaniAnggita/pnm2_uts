package com.a203110026.fanianggita_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a203110026.fanianggita_room.data.CatatanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO 5: Membuat kelas ViewModel
/* Peran ViewModel adalah memberikan data ke UI atau bertindak  sebagai pusat komunikasi antara Repositori dan UI.
ViewModel akan mengubah data dari Repositori, dari Flow ke LiveData, dan mengekspos daftar kata sebagai LiveData ke UI.
Hal ini memastikan bahwa setiap kali data berubah dalam database, UI akan otomatis diperbarui. */

@HiltViewModel
class ViewModelBeranda @Inject constructor(private val catatanRepository: CatatanRepository) : ViewModel() {

    /*
    MutableStateFlowdengan inisial yang diberikan valuedapat dibuat menggunakan fungsi konstruktor.MutableStateFlow(value)
     */
    private val cariQuery = MutableStateFlow("")

    val notes = cariQuery.flatMapLatest { query->
        catatanRepository.notes.map { it.filter { it.judulcatatan?.contains(query, ignoreCase = true) == true } }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Fungsi untuk melakukan query atau pencarian data catatan yang spesifik
    fun pencarianQueryChanged(query:String) = viewModelScope.launch {
        cariQuery.emit(query)
    }

}