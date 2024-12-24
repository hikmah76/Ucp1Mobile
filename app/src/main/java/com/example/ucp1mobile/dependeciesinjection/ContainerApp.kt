package com.example.ucp1mobile.dependeciesinjection

import android.content.Context
import com.example.ucp1mobile.data.database.KrsDatabase
import com.example.ucp1mobile.data.repository.LocalRepositoryDosen
import com.example.ucp1mobile.data.repository.LocalRepositoryMth
import com.example.ucp1mobile.data.repository.RepositoryDosen
import com.example.ucp1mobile.data.repository.RepositoryMth
//Dependency Injection untuk menyediakan repository data
interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMth: RepositoryMth
}


class ContainerApp (private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao())
    }
    override val repositoryMth: RepositoryMth by lazy {
        LocalRepositoryMth(KrsDatabase.getDatabase(context).matakuliahDao())
    }
}