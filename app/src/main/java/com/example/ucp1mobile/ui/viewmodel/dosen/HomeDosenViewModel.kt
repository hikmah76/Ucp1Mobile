package com.example.ucp1mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.data.repository.RepositoryDosen
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeDosenViewModel(
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {
    val homeUiState: StateFlow<HomeDosenUIState> = repositoryDosen.getAllDosen().filterNotNull()
        .filterNotNull()
        .map { dosen ->
            HomeDosenUIState(
                listDosen = dosen,
                isLoading = false
            )
        }
        .onStart {
            emit(HomeDosenUIState(isLoading = true, listDosen = emptyList()))
        }
        .catch { exception ->
            emit(
                HomeDosenUIState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi kesalahan",
                    listDosen = emptyList()
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDosenUIState(isLoading = true, listDosen = emptyList())
        )

    private val _detailUiState = MutableStateFlow(DetailDosenState())
    val detailUiState: StateFlow<DetailDosenState> = _detailUiState.asStateFlow()

    fun getDosenByNidn(nidn: String) {
        viewModelScope.launch {
            repositoryDosen.getDosen(nidn)
                .collect { dosen ->
                    _detailUiState.value = DetailDosenState(
                        isLoading = false,
                        detailUiEvent = dosen.toDosenEvent(),
                        isDataAvailable = true
                    )
                }
        }
    }

    fun deleteDosen() {
        viewModelScope.launch {
            try {
                _detailUiState.value.detailUiEvent.toDosen().let { dosen ->
                    repositoryDosen.deleteDosen(dosen)
                }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}

data class HomeDosenUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val listDosen: List<Dosen>
)

data class DetailDosenState(
    val isLoading: Boolean = false,
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isDataAvailable: Boolean = false
)

data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)

fun Dosen.toDosenEvent(): DosenEvent = DosenEvent(
    nidn = this.nidn,
    nama = this.nama,
    jenisKelamin = this.jenisKelamin
)

fun DosenEvent.toDosen(): Dosen = Dosen(
    nidn = this.nidn,
    nama = this.nama,
    jenisKelamin = this.jenisKelamin
)