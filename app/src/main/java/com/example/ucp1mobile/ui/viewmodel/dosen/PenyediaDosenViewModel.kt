package com.example.ucp1mobile.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp1mobile.KrsApp

object PenyediaDosenViewModel {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            DetailDosenViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            UpdateDosenViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)