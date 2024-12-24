package com.example.ucp1mobile.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
import com.example.ucp1mobile.ui.navigation.AlamatNavigasi
import com.example.ucp1mobile.ui.viewmodel.MatakuliahViewModel
import kotlinx.coroutines.launch
//Untuk tampilan form input matakuliah, termasuk validasi, pengisian data, dan dropdown dosen pengampu, serta menyimpan data setelah validasi berhasil.
object DestinasiInsertMth : AlamatNavigasi {
    override val route: String = "insert_mth"
}

@Composable
fun InsertMthView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = MatakuliahViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val dosenList = viewModel.dosenList.collectAsState(initial = emptyList())

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(padding),
        ) {
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            InsertBodyMth(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                },
                mataKuliahList = uiState.matakuliahList,
                dosenList = dosenList.value
            )
        }
    }
}

@Composable
fun InsertBodyMth(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahViewModel.MatakuliahEvent) -> Unit,
    uiState: MatakuliahViewModel.MthUIState,
    onClick: () -> Unit,
    mataKuliahList: List<Matakuliah>,
    dosenList: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            mahasiswaEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormMatakuliah(
    mahasiswaEvent: MatakuliahViewModel.MatakuliahEvent = MatakuliahViewModel.MatakuliahEvent(),
    onValueChange: (MatakuliahViewModel.MatakuliahEvent) -> Unit,
    errorState: MatakuliahViewModel.FormErrorState = MatakuliahViewModel.FormErrorState(),
    modifier: Modifier = Modifier,
    dosenList: List<String> = emptyList()
) {
    val jenisMatakuliah = listOf(
        "Matakuliah Wajib",
        "Matakuliah Peminatan"
    )
    var expandedJenis by rememberSaveable { mutableStateOf(false) }
    var expandedDosen by rememberSaveable { mutableStateOf(false) }
    var selectedMatakuliah by remember { mutableStateOf("") }
    var selectedDosen by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Kode
        OutlinedTextField(
            value = mahasiswaEvent.kode,
            onValueChange = { onValueChange(mahasiswaEvent.copy(kode = it)) },
            label = { Text("Kode Matakuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Matakuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.kode != null) {
            Text(text = errorState.kode, color = MaterialTheme.colorScheme.error)
        }

        // Nama
        OutlinedTextField(
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama Matakuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Matakuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.nama != null) {
            Text(text = errorState.nama, color = MaterialTheme.colorScheme.error)
        }

        // SKS
        OutlinedTextField(
            value = mahasiswaEvent.sks,
            onValueChange = { onValueChange(mahasiswaEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan jumlah SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.sks != null) {
            Text(text = errorState.sks, color = MaterialTheme.colorScheme.error)
        }

        // Semester
        OutlinedTextField(
            value = mahasiswaEvent.semester,
            onValueChange = { onValueChange(mahasiswaEvent.copy(semester = it)) },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.semester != null) {
            Text(text = errorState.semester, color = MaterialTheme.colorScheme.error)
        }

        // Jenis Dropdown
        OutlinedTextField(
            value = selectedMatakuliah,
            onValueChange = { },
            label = { Text("Jenis Matakuliah") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedJenis = !expandedJenis }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expandedJenis,
            onDismissRequest = { expandedJenis = false },
            modifier = Modifier.heightIn(max = 200.dp)
        ) {
            jenisMatakuliah.forEach { jenis ->
                DropdownMenuItem(
                    text = { Text(jenis) },
                    onClick = {
                        selectedMatakuliah = jenis
                        expandedJenis = false
                        onValueChange(mahasiswaEvent.copy(jenis = jenis))
                    }
                )
            }
        }

        // Dosen Pengampu Dropdown
        OutlinedTextField(
            value = selectedDosen,
            onValueChange = { },
            label = { Text("Dosen Pengampu") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedDosen = !expandedDosen }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expandedDosen,
            onDismissRequest = { expandedDosen = false },
            modifier = Modifier.heightIn(max = 200.dp)
        ) {
            dosenList.forEach { dosen ->
                DropdownMenuItem(
                    text = { Text(dosen) },
                    onClick = {
                        selectedDosen = dosen
                        expandedDosen = false
                        onValueChange(mahasiswaEvent.copy(dosenPengampu = dosen))
                    }
                )
            }
        }
    }
}