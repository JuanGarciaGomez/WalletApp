package com.example.walletapp.data.prefs

import android.app.Application

class FingerLoginOption : Application() {

    companion object{
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}