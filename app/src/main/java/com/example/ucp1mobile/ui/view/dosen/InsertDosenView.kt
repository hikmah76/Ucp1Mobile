package com.example.ucp1mobile.ui.view.dosen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
import com.example.ucp1mobile.ui.viewmodel.DosenViewModel
import com.example.ucp1mobile.ui.viewmodel.PenyediaDosenViewModel
import kotlinx.coroutines.launch
// Menampilkan formulir input data dosen dengan validasi dan snackbar pesan dan data dosen disimpan menggunakan ViewModel
@Composable
fun InsertDosenView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dosen"
            )
            FormDosen(
                mahasiswaEvent = uiState.dosenEvent,
                onValueChange = { viewModel.updateState(it) },
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}

@Composable
private fun FormDosen(
    mahasiswaEvent: DosenViewModel.DosenEvent,
    onValueChange: (DosenViewModel.DosenEvent) -> Unit,
    errorState: DosenViewModel.FormErrorState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = mahasiswaEvent.nidn,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nidn = it)) },
            label = { Text("NIDN") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorState.nidn != null,
            supportingText = { if (errorState.nidn != null) Text(errorState.nidn) }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorState.nama != null,
            supportingText = { if (errorState.nama != null) Text(errorState.nama) }
        )

        Spacer(Modifier.height(8.dp))

        Text("Jenis Kelamin")
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Laki-laki", "Perempuan").forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = { onValueChange(mahasiswaEvent.copy(jenisKelamin = jk)) }
                    )
                    Text(jk)
                }
            }
        }
        if (errorState.jenisKelamin != null) {
            Text(
                text = errorState.jenisKelamin,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}