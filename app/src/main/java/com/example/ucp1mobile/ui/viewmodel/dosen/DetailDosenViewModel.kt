package com.example.ucp1mobile.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.ui.navigation.DestinasiDosenUpdate
import com.example.ucp1mobile.data.repository.RepositoryDosen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
// Mengelola detail dosen, memuat data, menangani error, dan menghapus dosen
class DetailDosenViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDosenUpdate.NIDN])

    val detailUiState: StateFlow<DetailUiState> = repositoryDosen.getDosen(_nidn)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch { throwable ->
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(isLoading = true)
        )

    fun deleteDosen() {
        detailUiState.value.detailUiEvent.toDosenEntity().let { dosen ->
            viewModelScope.launch {
                repositoryDosen.deleteDosen(dosen)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: DosenViewModel.DosenEvent = DosenViewModel.DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenViewModel.DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenViewModel.DosenEvent()
}

fun Dosen.toDetailUiEvent(): DosenViewModel.DosenEvent {
    return DosenViewModel.DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}