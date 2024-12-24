package com.example.ucp1mobile.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.ui.viewmodel.MatakuliahViewModel
//  Form input untuk menambah data mata kuliah, dengan validasi, dropdown untuk SKS, semester, jenis, dan dosen pengampu, serta tombol simpan.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertFormMth(
    modifier: Modifier = Modifier,
    uiState: MatakuliahViewModel.MthUIState,
    onValueChange: (MatakuliahViewModel.MatakuliahEvent) -> Unit,
    onClick: () -> Unit,
    mataKuliahList: List<Matakuliah>,
    dosenList: List<String>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.matakuliahEvent.kode,
            onValueChange = { onValueChange(uiState.matakuliahEvent.copy(kode = it)) },
            label = { Text("Kode") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            isError = uiState.isEntryValid.kode != null,
            supportingText = { Text(uiState.isEntryValid.kode ?: "") }
        )

        OutlinedTextField(
            value = uiState.matakuliahEvent.nama,
            onValueChange = { onValueChange(uiState.matakuliahEvent.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.isEntryValid.nama != null,
            supportingText = { Text(uiState.isEntryValid.nama ?: "") }
        )

        // SKS Dropdown
        var expandedSks by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedSks,
            onExpandedChange = { expandedSks = it }
        ) {
            OutlinedTextField(
                value = uiState.matakuliahEvent.sks,
                onValueChange = {},
                readOnly = true,
                label = { Text("SKS") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSks) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.isEntryValid.sks != null,
                supportingText = { Text(uiState.isEntryValid.sks ?: "") }
            )
            ExposedDropdownMenu(
                expanded = expandedSks,
                onDismissRequest = { expandedSks = false }
            ) {
                listOf("1", "2", "3", "4").forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onValueChange(uiState.matakuliahEvent.copy(sks = item))
                            expandedSks = false
                        }
                    )
                }
            }
        }

        // Semester Dropdown
        var expandedSemester by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedSemester,
            onExpandedChange = { expandedSemester = it }
        ) {
            OutlinedTextField(
                value = uiState.matakuliahEvent.semester,
                onValueChange = {},
                readOnly = true,
                label = { Text("Semester") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSemester) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.isEntryValid.semester != null,
                supportingText = { Text(uiState.isEntryValid.semester ?: "") }
            )
            ExposedDropdownMenu(
                expanded = expandedSemester,
                onDismissRequest = { expandedSemester = false }
            ) {
                listOf("1", "2", "3", "4", "5", "6", "7", "8").forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onValueChange(uiState.matakuliahEvent.copy(semester = item))
                            expandedSemester = false
                        }
                    )
                }
            }
        }

        // Jenis Dropdown
        var expandedJenis by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedJenis,
            onExpandedChange = { expandedJenis = it }
        ) {
            OutlinedTextField(
                value = uiState.matakuliahEvent.jenis,
                onValueChange = {},
                readOnly = true,
                label = { Text("Jenis") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedJenis) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.isEntryValid.jenis != null,
                supportingText = { Text(uiState.isEntryValid.jenis ?: "") }
            )
            ExposedDropdownMenu(
                expanded = expandedJenis,
                onDismissRequest = { expandedJenis = false }
            ) {
                listOf("Wajib", "Pilihan").forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onValueChange(uiState.matakuliahEvent.copy(jenis = item))
                            expandedJenis = false
                        }
                    )
                }
            }
        }

        // Dosen Pengampu Dropdown
        var expandedDosen by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedDosen,
            onExpandedChange = { expandedDosen = it }
        ) {
            OutlinedTextField(
                value = uiState.matakuliahEvent.dosenPengampu,
                onValueChange = {},
                readOnly = true,
                label = { Text("Dosen Pengampu") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDosen) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.isEntryValid.dosenPengampu != null,
                supportingText = { Text(uiState.isEntryValid.dosenPengampu ?: "") }
            )
            ExposedDropdownMenu(
                expanded = expandedDosen,
                onDismissRequest = { expandedDosen = false }
            ) {
                dosenList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onValueChange(uiState.matakuliahEvent.copy(dosenPengampu = item))
                            expandedDosen = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Simpan", modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}