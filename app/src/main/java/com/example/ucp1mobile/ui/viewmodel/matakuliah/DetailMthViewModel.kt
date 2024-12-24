package com.example.ucp1mobile.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.repository.RepositoryMth
import com.example.ucp1mobile.ui.navigation.DestinasiMatakuliahDetail
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailMthViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMth: RepositoryMth
) : ViewModel() {
    private val kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahDetail.KODE]).toString()

    private val _detailUiState = MutableStateFlow(DetailMatakuliahState())
    val detailUiState: StateFlow<DetailMatakuliahState> = _detailUiState.asStateFlow()

    init {
        loadMatakuliahDetails()
    }

    private fun loadMatakuliahDetails() {
        viewModelScope.launch {
            try {
                println("Debug: Starting to fetch matakuliah with kode: $kode")
                repositoryMth.getMth(kode)
                    .collect { matakuliah ->
                        println("Debug: Received matakuliah data: $matakuliah")
                        _detailUiState.value = DetailMatakuliahState(
                            detailUiEvent = matakuliah.toUiEvent(),
                            isLoading = false,
                            isUiEventNotEmpty = true
                        )
                    }
            } catch (e: Exception) {
                println("Debug: Error fetching matakuliah: ${e.message}")
                _detailUiState.value = DetailMatakuliahState(
                    isLoading = false,
                    errorMessage = e.message ?: "Terjadi kesalahan"
                )
            }
        }
    }

    fun deleteMatakuliah() {
        viewModelScope.launch {
            try {
                detailUiState.value.detailUiEvent.toMatakuliah().let { matakuliah ->
                    repositoryMth.deleteMth(matakuliah)
                }
            } catch (e: Exception) {
                println("Debug: Error saat delete: ${e.message}")
            }
        }
    }
}

data class DetailMatakuliahState(
    val detailUiEvent: MatakuliahViewModel.MatakuliahEvent = MatakuliahViewModel.MatakuliahEvent(),
    val isLoading: Boolean = true,
    val isUiEventNotEmpty: Boolean = false,
    val errorMessage: String = ""
)