package com.example.ucp1mobile.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp1mobile.ui.navigation.DestinasiDosenDetail
import com.example.ucp1mobile.ui.navigation.DestinasiDosenInsert
import com.example.ucp1mobile.ui.navigation.DestinasiDosenUpdate
import com.example.ucp1mobile.ui.navigation.DestinasiMatakuliahDetail
import com.example.ucp1mobile.ui.navigation.DestinasiMatakuliahInsert
import com.example.ucp1mobile.ui.navigation.DestinasiMatakuliahUpdate
import com.example.ucp1mobile.ui.view.dosen.*
import com.example.ucp1mobile.ui.view.matakuliah.*
//Berfungsinya untuk menampilkan halaman detail, tambah, dan ubah data dosen serta matakuliah
@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Dosen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Dosen.route) {
                HomeDosenView(
                    onDetailKlik = { nidn ->
                        navController.navigate("${DestinasiDosenDetail.route}/$nidn")
                    },
                    onTambahDosen = {
                        navController.navigate(DestinasiDosenInsert.route)
                    }
                )
            }

            composable(NavigationItem.Matakuliah.route) {
                HomeMthView(
                    onDetailClick = { kode ->
                        navController.navigate("${DestinasiMatakuliahDetail.route}/$kode")
                    },
                    onAddMth = {
                        navController.navigate(DestinasiMatakuliahInsert.route)
                    }
                )
            }

            // Detail Dosen
            composable(
                route = DestinasiDosenDetail.routeWithArg,
                arguments = listOf(navArgument(DestinasiDosenDetail.NIDN) { type = NavType.StringType })
            ) { backStackEntry ->
                val nidn = backStackEntry.arguments?.getString(DestinasiDosenDetail.NIDN)
                nidn?.let { id ->
                    DetailDosenView(
                        onBack = { navController.popBackStack() },
                        onEditClick = { navController.navigate("${DestinasiDosenUpdate.route}/$id") },
                        onDeleteClick = { navController.popBackStack() }
                    )
                }
            }

            // Insert Dosen
            composable(DestinasiDosenInsert.route) {
                InsertDosenView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }

            // Update Dosen
            composable(
                route = DestinasiDosenUpdate.routeWithArg,
                arguments = listOf(navArgument(DestinasiDosenUpdate.NIDN) { type = NavType.StringType })
            ) {
                UpdateDosenView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }

            // Detail Matakuliah
            composable(
                route = DestinasiMatakuliahDetail.routeWithArg,
                arguments = listOf(navArgument(DestinasiMatakuliahDetail.KODE) { type = NavType.StringType })
            ) { backStackEntry ->
                val kode = backStackEntry.arguments?.getString(DestinasiMatakuliahDetail.KODE)
                kode?.let { id ->
                    DetailMthView(
                        onBack = { navController.popBackStack() },
                        onEditClick = { navController.navigate("${DestinasiMatakuliahUpdate.route}/$id") },
                        onDeleteClick = { navController.popBackStack() }
                    )
                }
            }

            // Insert Matakuliah
            composable(DestinasiMatakuliahInsert.route) {
                InsertMthView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }

            // Update Matakuliah
            composable(
                route = DestinasiMatakuliahUpdate.routeWithArg,
                arguments = listOf(navArgument(DestinasiMatakuliahUpdate.KODE) { type = NavType.StringType })
            ) {
                UpdateMthView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}