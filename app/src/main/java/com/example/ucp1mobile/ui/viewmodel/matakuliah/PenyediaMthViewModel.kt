package com.example.room10.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.example.roomlocaldb1.ui.viewmodel.HomeMthViewModel
import com.example.roomlocaldb1.ui.viewmodel.UpdateMthViewModel
import com.example.ucp1mobile.ui.viewmodel.MatakuliahViewModel
import com.example.ucp1mobile.KrsApp
import com.example.ucp1mobile.ui.viewmodel.DetailMthViewModel

object PenyediaMthViewModel {
    val Factory = viewModelFactory {
        initializer {
            MatakuliahViewModel(
                repositoryMatakuliah = krsApp().containerApp.repositoryMth,
                repositoryDosen = krsApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeMthViewModel(
                repositoryMth = krsApp().containerApp.repositoryMth
            )
        }
        initializer {
            DetailMthViewModel(
                savedStateHandle = createSavedStateHandle(),
                repositoryMth = krsApp().containerApp.repositoryMth
            )
        }
        initializer {
            UpdateMthViewModel(
                savedStateHandle = createSavedStateHandle(),
                repositoryMth = krsApp().containerApp.repositoryMth
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)