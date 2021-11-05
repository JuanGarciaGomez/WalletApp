package com.example.walletapp.data.prefs

import android.content.Context

class Prefs (context: Context){

    private val appContext = context.applicationContext

    private val SHARED_NAME = "FingerLogin"
    private val SHARED_FINGER_ACTIVE = "fingerActive"
    private val storage= appContext.getSharedPreferences(SHARED_NAME,0);

    fun saveFingerLogin(fingerActive:Boolean){
        storage.edit().putBoolean(SHARED_FINGER_ACTIVE,fingerActive).apply()
    }

    fun getFingerLogin():Boolean{
        return storage.getBoolean(SHARED_FINGER_ACTIVE,false)
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

}