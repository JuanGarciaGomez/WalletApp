package com.example.walletapp.data.prefs

import android.content.Context
import com.example.walletapp.utils.Utils.Companion.encryptPass
import com.example.walletapp.utils.Utils.Companion.encryptUser
import com.example.walletapp.utils.Utils.Companion.passKey
import com.example.walletapp.utils.Utils.Companion.userKey

class Prefs(context: Context) {

    private val appContext = context.applicationContext

    private val SHARED_NAME = "FingerLogin"
    private val SHARED_FINGER_MODE = "fingerMode"
    private val SHARED_USER = "user"
    private val SHARED_KEY = "key"
    private val storage = appContext.getSharedPreferences(SHARED_NAME, 0);

    /**
     * PermisosHuella dactilar
     * fingerMode = 1 Concedido, 2 Denegado, 0 Desactivado
     */
    fun saveFingerLogin(fingerMode: Int) {
        storage.edit().putInt(SHARED_FINGER_MODE, fingerMode).apply()
    }

    fun getFingerLogin(): Int {
        return storage.getInt(SHARED_FINGER_MODE, 0)
    }
    /**
     * Save user and password with cryptMode
     */
    fun saveCredentialsLogin(user: String, key: String) {
        storage.edit().putString(SHARED_USER, encryptUser(user, userKey)).apply()
        storage.edit().putString(SHARED_KEY, encryptPass(key, passKey)).apply()
    }

    fun getUsersLogin(): String {
        storage.getString(SHARED_USER, "")?.let { return it }
        return ""
    }

    fun getKeyLogin(): String? {
        return storage.getString(SHARED_KEY, "")
    }

    /**
     * Clear SharePreferences
     */
    fun wipe() {
        storage.edit().clear().apply()
    }

}