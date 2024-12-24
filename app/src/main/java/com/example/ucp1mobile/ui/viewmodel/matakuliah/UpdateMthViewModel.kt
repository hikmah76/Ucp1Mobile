package com.example.roomlocaldb1.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.data.repository.RepositoryMth
import com.example.ucp1mobile.ui.navigation.DestinasiMatakuliahDetail
import com.example.ucp1mobile.ui.viewmodel.MatakuliahViewModel
import com.example.ucp1mobile.ui.viewmodel.toMatakuliah
import com.example.ucp1mobile.ui.viewmodel.toUiEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UpdateMthViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMth: RepositoryMth
) : ViewModel() {
    private val kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahDetail.KODE])

    var updateUiState by mutableStateOf(MthUIState())
        private set

    init {
        loadMatakuliah()
    }

    private fun loadMatakuliah() {
        viewModelScope.launch {
            updateUiState = updateUiState.copy(isLoading = true)
            try {
                repositoryMth.getMth(kode).collect { matakuliah ->
                    println("Debug: Loaded matakuliah: $matakuliah")
                    updateUiState = updateUiState.copy(
                        matakuliahEvent = matakuliah.toUiEvent(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                println("Debug: Error loading matakuliah: ${e.message}")
                updateUiState = updateUiState.copy(
                    snackBarMessage = "Gagal memuat data: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun updateState(matakuliahEvent: MatakuliahViewModel.MatakuliahEvent) {
        updateUiState = updateUiState.copy(matakuliahEvent = matakuliahEvent)
    }

    fun updateMatakuliah() {
        viewModelScope.launch {
            try {
                repositoryMth.updateMatakuliah(updateUiState.matakuliahEvent.toMatakuliah())
                updateUiState = updateUiState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                )
            } catch (e: Exception) {
                updateUiState = updateUiState.copy(
                    snackBarMessage = "Data gagal diupdate: ${e.message}"
                )
            }
        }
    }

    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

    fun validateFields(): Boolean {
        val event = updateUiState.matakuliahEvent
        val errorState = MatakuliahViewModel.FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    data class MthUIState(
        val matakuliahEvent: MatakuliahViewModel.MatakuliahEvent = MatakuliahViewModel.MatakuliahEvent(),
        val isEntryValid: MatakuliahViewModel.FormErrorState = MatakuliahViewModel.FormErrorState(),
        val snackBarMessage: String? = null,
        val isLoading: Boolean = false
    )
}