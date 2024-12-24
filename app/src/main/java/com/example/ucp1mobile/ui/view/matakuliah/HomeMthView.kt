package com.example.ucp1mobile.ui.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.room10.ui.viewmodel.PenyediaMthViewModel
import com.example.roomlocaldb1.ui.viewmodel.HomeMthViewModel
import com.example.ucp1mobile.data.entity.Matakuliah
import com.example.ucp1mobile.ui.customwidget.CustomTopAppBar
//Untuk  tampilan daftar mata kuliah dengan fitur loading, kosong, dan detail mata kuliah
@Composable
fun HomeMthView(
    viewModel: HomeMthViewModel = viewModel(factory = PenyediaMthViewModel.Factory),
    onAddMth: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopAppBar(
            judul = "Daftar Matakuliah",
            showBackButton = false,
            onBack = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                homeUiState.isLoading -> LoadingScreen()
                homeUiState.listMth.isEmpty() -> EmptyScreen()
                else -> MatakuliahList(
                    matakuliahs = homeUiState.listMth,
                    onItemClick = onDetailClick
                )
            }

            FloatingActionButton(
                onClick = onAddMth,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, "Tambah Matakuliah")
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Belum ada data matakuliah",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun MatakuliahList(
    matakuliahs: List<Matakuliah>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        items(matakuliahs) { matakuliah ->
            MatakuliahCard(
                matakuliah = matakuliah,
                onClick = { onItemClick(matakuliah.kode) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatakuliahCard(
    matakuliah: Matakuliah,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.AccountBox, "Matakuliah")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${matakuliah.kode} - ${matakuliah.nama}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, "Dosen")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = matakuliah.dosenPengampu,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, "Info")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SKS: ${matakuliah.sks} | Semester: ${matakuliah.semester}",
                    fontSize = 14.sp
                )
            }
        }
    }
}