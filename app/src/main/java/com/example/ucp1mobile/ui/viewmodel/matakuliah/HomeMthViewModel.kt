package com.example.roomlocaldb1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp1mobile.data.entity.Dosen

import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.data.repository.RepositoryMth

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMthViewModel (  // Mengelola dan mengubah state data mahasiswa.
    private val repositoryMth: RepositoryMth
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = repositoryMth.getAllMth()
        .filterNotNull()
        .map { HomeUiState (
            listMth = it.toList(),
            isLoading = false
        ) }
        .onStart {
            emit(HomeUiState(isLoading = true, listMth = emptyList()))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                    listMth = emptyList()
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true, listMth = emptyList(),)
        )
}

data class HomeUiState(

    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val listMth: List<Matakuliah>
) {

}