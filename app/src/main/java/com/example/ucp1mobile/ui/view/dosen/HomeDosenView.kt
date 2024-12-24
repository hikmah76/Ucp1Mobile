package com.example.ucp1mobile.ui.view.dosen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp1mobile.data.entity.Dosen
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
import com.example.ucp1mobile.ui.viewmodel.DosenViewModel
import androidx.compose.runtime.collectAsState
//  Menampilkan daftar dosen dengan opsi tambah, detail, dan informasi dosen dalam UI menggunakan
@Composable
fun HomeDosenView(
    onTambahDosen: () -> Unit = {},
    onDetailKlik: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = DosenViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Daftar Dosen",
                showBackButton = false,
                onBack = {},
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTambahDosen,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen"
                )
            }
        }
    ) { innerPadding ->
        ListDosen(
            listDosen = uiState.daftarDosen,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onItemClick = onDetailKlik
        )
    }
}

@Composable
fun ListDosen(
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (listDosen.isEmpty()) {
            item {
                Text(
                    text = "Tidak ada data Dosen",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        items(listDosen) { dosen ->
            CardDosen(
                dosen = dosen,
                onItemClick = { onItemClick(dosen.nidn) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDosen(
    dosen: Dosen,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Text(
                    text = dosen.nama,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = "NIDN: ${dosen.nidn}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Jenis Kelamin: ${dosen.jenisKelamin}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}