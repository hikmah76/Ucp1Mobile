package com.example.ucp1mobile.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.room10.ui.viewmodel.PenyediaMthViewModel
import com.example.roomlocaldb1.ui.viewmodel.UpdateMthViewModel
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
import com.example.ucp1mobile.ui.viewmodel.MatakuliahViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
//Menampilkan tampilan update matakuliah dengan validasi input, indikator loading, menampilkan snackbar, dan melakukan aksi update matakuliah saat validasi berhasil
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMthView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMthViewModel = viewModel(factory = PenyediaMthViewModel.Factory),
    matakuliahViewModel: MatakuliahViewModel = viewModel(factory = MatakuliahViewModel.Factory)
) {
    val uiState = viewModel.updateUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dosenList = matakuliahViewModel.dosenList.collectAsState(initial = emptyList())

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CustomTopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                InsertFormMth(
                    uiState = MatakuliahViewModel.MthUIState(
                        matakuliahEvent = uiState.matakuliahEvent,
                        isEntryValid = uiState.isEntryValid
                    ),
                    onValueChange = { updateEvent ->
                        viewModel.updateState(updateEvent)
                    },
                    onClick = {
                        coroutineScope.launch {
                            if (viewModel.validateFields()) {
                                viewModel.updateMatakuliah()
                                delay(600)
                                onNavigate()
                            }
                        }
                    },
                    mataKuliahList = emptyList(),
                    dosenList = dosenList.value
                )
            }
        }
    }
}