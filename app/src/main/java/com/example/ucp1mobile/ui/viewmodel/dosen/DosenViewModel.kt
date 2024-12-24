package com.example.ucp1mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp1mobile.KrsApp
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.repository.RepositoryDosen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {
    private val _uiState = MutableStateFlow(DosenUIState())
    val uiState: StateFlow<DosenUIState> = _uiState

    init {
        getDaftarDosen()
    }

    private fun getDaftarDosen() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { daftar ->
                _uiState.update { current ->
                    current.copy(daftarDosen = daftar)
                }
            }
        }
    }

    fun updateState(dosenEvent: DosenEvent) {
        _uiState.update { current ->
            current.copy(dosenEvent = dosenEvent)
        }
    }

    private fun validateFields(): Boolean {
        val event = _uiState.value.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        _uiState.update { current ->
            current.copy(isEntryValid = errorState)
        }
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = _uiState.value.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    _uiState.update { current ->
                        current.copy(
                            snackBarMessage = "Data berhasil disimpan",
                            dosenEvent = DosenEvent(),
                            isEntryValid = FormErrorState()
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { current ->
                        current.copy(snackBarMessage = "Data gagal disimpan")
                    }
                }
            }
        } else {
            _uiState.update { current ->
                current.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
            }
        }
    }

    fun resetSnackBarMessage() {
        _uiState.update { current ->
            current.copy(snackBarMessage = null)
        }
    }

    data class DosenUIState(
        val daftarDosen: List<Dosen> = listOf(),
        val dosenEvent: DosenEvent = DosenEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null
    )

    data class FormErrorState(
        val nidn: String? = null,
        val nama: String? = null,
        val jenisKelamin: String? = null
    ) {
        fun isValid(): Boolean {
            return nidn == null && nama == null && jenisKelamin == null
        }
    }

    data class DosenEvent(
        val nidn: String = "",
        val nama: String = "",
        val jenisKelamin: String = ""
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
                val repository = application.containerApp.repositoryDosen
                DosenViewModel(repository)
            }
        }
    }
}

fun DosenViewModel.DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

