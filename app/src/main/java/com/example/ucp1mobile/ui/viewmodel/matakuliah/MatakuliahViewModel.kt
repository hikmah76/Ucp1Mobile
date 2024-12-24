package com.example.ucp1mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.KrsApp
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.data.repository.RepositoryDosen
import com.example.ucp1mobile.data.repository.RepositoryMth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MatakuliahViewModel(
    private val repositoryMatakuliah: RepositoryMth,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {
    var uiState by mutableStateOf(MthUIState())
        private set

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    // Tambahkan StateFlow untuk daftar dosen
    private val _dosenList = MutableStateFlow<List<String>>(emptyList())
    val dosenList: StateFlow<List<String>> = _dosenList.asStateFlow()

    init {
        getDaftarDosen()
    }

    private fun getDaftarDosen() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen()
                .map { dosenList -> dosenList.map { it.nama } }
                .collect { namaDosenList ->
                    _dosenList.value = namaDosenList
                }
        }
    }

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(matakuliahEvent = matakuliahEvent)
    }

    fun saveData() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.inssertMth(uiState.matakuliahEvent.toMatakuliah())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        matakuliahEvent = MatakuliahEvent()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(snackBarMessage = "Data gagal disimpan")
                }
            }
        }
    }

    fun deleteMatakuliah() {
        viewModelScope.launch {
            try {
                _detailUiState.value.detailUiEvent.toMatakuliah().let { matakuliah ->
                    repositoryMatakuliah.deleteMth(matakuliah)
                }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    fun getMatakuliahById(kode: String) {
        viewModelScope.launch {
            repositoryMatakuliah.getMth(kode)
                .collect { matakuliah ->
                    _detailUiState.value = DetailUiState(
                        detailUiEvent = matakuliah.toUiEvent(),
                        isLoading = false,
                        isUiEventNotEmpty = true
                    )
                }
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }

    private fun validateFields(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Data classes tetap sama
    data class MthUIState(
        val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null,
        val matakuliahList: List<Matakuliah> = listOf()
    )

    data class DetailUiState(
        val detailUiEvent: MatakuliahEvent = MatakuliahEvent(),
        val isLoading: Boolean = true,
        val isUiEventNotEmpty: Boolean = false
    )

    data class FormErrorState(
        val kode: String? = null,
        val nama: String? = null,
        val sks: String? = null,
        val semester: String? = null,
        val jenis: String? = null,
        val dosenPengampu: String? = null
    ) {
        fun isValid(): Boolean {
            return kode == null && nama == null && sks == null &&
                    semester == null && jenis == null && dosenPengampu == null
        }
    }

    data class MatakuliahEvent(
        val kode: String = "",
        val nama: String = "",
        val sks: String = "",
        val semester: String = "",
        val jenis: String = "",
        val dosenPengampu: String = ""
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
                val repositoryMth = application.containerApp.repositoryMth
                val repositoryDosen = application.containerApp.repositoryDosen
                MatakuliahViewModel(repositoryMth, repositoryDosen)
            }
        }
    }
}

// Extension functions tetap sama
fun MatakuliahViewModel.MatakuliahEvent.toMatakuliah(): Matakuliah {
    return Matakuliah(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}

fun Matakuliah.toUiEvent(): MatakuliahViewModel.MatakuliahEvent {
    return MatakuliahViewModel.MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}