package com.example.walletapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.prefs.FingerLoginOption.Companion.prefs
import com.example.walletapp.utils.Utils.Companion.decryptUser
import com.example.walletapp.utils.Utils.Companion.userKey

class LoginViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")


    init {
        email.value = decryptUser(prefs.getUsersLogin(), userKey)
        if (firebaseController.hasSession()) {
            navigation.value = NAVIGATION.GO_MAIN_VIEW
        }
    }

    fun login() {
        val emailLogin = email.value ?: ""
        val passwordLogin = password.value ?: ""
        try {

            if (emailLogin.isNotEmpty() && passwordLogin.isEmpty()) success.value =
                SUCCESS.FINGER_ACCESS
            if (emailLogin.isEmpty()
                || passwordLogin.isEmpty()
            ) error.value = ERROR.EMPTY_FIELDS
            else {
                val model = LoginModel(emailLogin, passwordLogin)
                model.auth({
                    navigation.value = NAVIGATION.GO_MAIN_VIEW
                    success.value = SUCCESS.LOGIN_SUCCESS
                    prefs.saveCredentialsLogin(emailLogin, passwordLogin)
                }, {
                    error.value = ERROR.WRONG_CREDENTIALS
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun register() {
        navigation.value = NAVIGATION.GO_REGISTER_VIEW
    }

    /*fun fingerAuth() {
        success.value = SUCCESS.FINGER_ACCESS
    }*/

}