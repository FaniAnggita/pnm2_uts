package com.a203110026.fanianggita_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a203110026.fanianggita_room.data.CatatanEntitas
import com.a203110026.fanianggita_room.data.CatatanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCatatanBaru @Inject constructor(private val catatanRepository: CatatanRepository) : ViewModel() {

    val catatanID = MutableStateFlow<Int?>(null)

    val note = catatanID.flatMapLatest {
        val note = it?.let { catatanRepository.getCatatan(it) }
        flowOf(note)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun setNoteId(id:Int) = viewModelScope.launch {
        catatanID.emit(id)
    }

    suspend fun updateNote(catatanEntitas: CatatanEntitas) = catatanRepository.PerbaruiCttn(catatanEntitas)
    suspend fun saveNote(catatanEntitas: CatatanEntitas) = catatanRepository.insertCatatanBaru(catatanEntitas)


}