package com.example.ucp1mobile.ui.customwidget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ucp1mobile.ui.viewmodel.DosenViewModel
// Untuk membuat form input data dosen dengan validasi, seperti NIDN, nama, jenis kelamin, serta tombol simpan untuk menyimpan data dosen
@Composable
fun InsertBodyDosen(
    modifier: Modifier = Modifier,
    onValueChange: (DosenViewModel.DosenEvent) -> Unit,
    uiState: DosenViewModel.DosenUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDosen(
            mahasiswaEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormDosen(
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