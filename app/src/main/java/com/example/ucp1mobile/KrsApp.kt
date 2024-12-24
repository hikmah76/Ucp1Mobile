package com.example.ucp1mobile

import android.app.Application
import android.util.Log
import com.example.ucp1mobile.dependeciesinjection.ContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        try {
            containerApp = ContainerApp(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}