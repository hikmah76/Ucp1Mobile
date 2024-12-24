package com.example.ucp1mobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ucp1mobile.ui.navigation.*
// Untuk item navigasi dengan route, title, dan icon
sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dosen : NavigationItem(
        route = DestinasiDosenHome.route,
        title = "Dosen",
        icon = Icons.Default.Person
    )
    object Matakuliah : NavigationItem(
        route = DestinasiMatakuliahHome.route,
        title = "Matakuliah",
        icon = Icons.Default.AccountBox
    )
}