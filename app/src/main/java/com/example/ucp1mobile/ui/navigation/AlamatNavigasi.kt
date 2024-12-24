package com.example.ucp1mobile.ui.navigation
// untuk menentukan alamat atau rute navigasi, seperti halaman untuk dosen dan matakuliah. Dengan  parameter seperti NIDN dan KODE
interface AlamatNavigasi {
    val route: String
}
object DestinasiDosenHome {
    const val route = "home_dosen"
}

object DestinasiDosenInsert {
    const val route = "insert_dosen"
}

object DestinasiDosenDetail {
    const val route = "detail_dosen"
    const val NIDN = "nidn"
    val routeWithArg = "$route/{$NIDN}"
}

object DestinasiDosenUpdate {
    const val route = "update_dosen"
    const val NIDN = "nidn"
    val routeWithArg = "$route/{$NIDN}"
}

// Matakuliah
object DestinasiMatakuliahHome : AlamatNavigasi {
    override val route = "home_matakuliah"
}

object DestinasiMatakuliahInsert : AlamatNavigasi {
    override val route = "insert_matakuliah"
}

object DestinasiMatakuliahDetail : AlamatNavigasi {
    override val route = "detail_matakuliah"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}

object DestinasiMatakuliahUpdate : AlamatNavigasi {
    override val route = "update_matakuliah"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}