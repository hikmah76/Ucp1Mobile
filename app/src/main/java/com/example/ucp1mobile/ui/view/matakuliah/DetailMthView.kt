package com.example.ucp1mobile.ui.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.room10.ui.viewmodel.PenyediaMthViewModel
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
import com.example.ucp1mobile.ui.viewmodel.DetailMthViewModel
import com.example.ucp1mobile.ui.viewmodel.toMatakuliah
// menampilkan tampilan detail matakuliah, termasuk informasi seperti kode, nama, SKS, semester, jenis, dosen pengampu, serta tombol untuk edit dan hapus data
@Composable
fun DetailMthView(
    modifier: Modifier = Modifier,
    viewModel: DetailMthViewModel = viewModel(factory = PenyediaMthViewModel.Factory),
    onBack: () -> Unit = { },
    onDeleteClick: () -> Unit = { },
    onEditClick: (String) -> Unit = { }
) {
    val detailUiState by viewModel.detailUiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        CustomTopAppBar(
            judul = "Detail Matakuliah",
            showBackButton = true,
            onBack = onBack
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                detailUiState.isLoading -> {
                    println("Debug: Showing loading state")
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                detailUiState.errorMessage.isNotEmpty() -> {
                    Text(
                        text = detailUiState.errorMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                detailUiState.isUiEventNotEmpty -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        ItemDetailMth(mth = detailUiState.detailUiEvent.toMatakuliah())
                        Spacer(modifier = Modifier.height(16.dp))
                        DeleteButton(
                            onDeleteClick = {
                                viewModel.deleteMatakuliah()
                                onDeleteClick()
                            }
                        )
                    }
                }
                else -> {
                    EmptyState("Data tidak ditemukan")
                }
            }

            FloatingActionButton(
                onClick = { onEditClick(detailUiState.detailUiEvent.kode) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Edit, "Edit Matakuliah")
            }
        }
    }
}

@Composable
private fun ItemDetailMth(mth: Matakuliah, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailRow("KODE", mth.kode)
            DetailRow("NAMA", mth.nama)
            DetailRow("SKS", mth.sks)
            DetailRow("SEMESTER", mth.semester)
            DetailRow("JENIS", mth.jenis)
            DetailRow("DOSEN PENGAMPU", mth.dosenPengampu)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun DeleteButton(onDeleteClick: () -> Unit, modifier: Modifier = Modifier) {
    var showConfirmation by remember { mutableStateOf(false) }

    Button(
        onClick = { showConfirmation = true },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text("Hapus")
    }

    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmation = false
                        onDeleteClick()
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmation = false }) {
                    Text("Tidak")
                }
            }
        )
    }
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}