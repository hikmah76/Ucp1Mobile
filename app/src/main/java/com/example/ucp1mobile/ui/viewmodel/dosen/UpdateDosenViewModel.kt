package com.example.ucp1mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.repository.RepositoryDosen
import com.example.ucp1mobile.ui.navigation.DestinasiDosenUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateDosenViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {
    var updateUiState by mutableStateOf(DosenViewModel.DosenUIState())
        private set
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDosenUpdate.NIDN])

    init {
        viewModelScope.launch {
            updateUiState = repositoryDosen.getDosen(_nidn)
                .filterNotNull()
                .first()
                .toUIStateDosen()
        }
    }

    fun updateState(dosenEvent: DosenViewModel.DosenEvent) {
        updateUiState = updateUiState.copy(dosenEvent = dosenEvent)
    }

    fun validateFields(): Boolean {
        val event = updateUiState.dosenEvent
        val errorState = DosenViewModel.FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.updateDosen(currentEvent.toDosenEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        dosenEvent = DosenViewModel.DosenEvent(),
                        isEntryValid = DosenViewModel.FormErrorState()
                    )
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(snackBarMessage = "Data gagal diupdate")
        }
    }

    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Dosen.toUIStateDosen(): DosenViewModel.DosenUIState {
    return DosenViewModel.DosenUIState(dosenEvent = this.toDetailUiEvent())
}