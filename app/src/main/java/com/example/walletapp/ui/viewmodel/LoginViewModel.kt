package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.prefs.FingerLoginOption.Companion.prefs

class LoginViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")


    init {
        if (firebaseController.hasSession()) {
            navigation.value = NAVIGATIONS.GO_MAIN_VIEW
        }
    }

    fun login() {
        val emailLogin = email.value ?: ""
        val passwordLogin = password.value ?: ""
        try {

            if (emailLogin.isEmpty()
                || passwordLogin.isEmpty()
            ) error.value = ERRORES.EMPTY_FIELDS
            else {
                val model = LoginModel(emailLogin, passwordLogin)
                model.auth({
                    navigation.value = NAVIGATIONS.GO_MAIN_VIEW
                    success.value = SUCCESS.LOGIN_SUCCESS
                 //   prefs.saveFingerLogin(true)
                }, {
                    error.value = ERRORES.WRONG_CREDENTIALS
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun register() {
        navigation.value = NAVIGATIONS.GO_REGISTER_VIEW
    }

    fun fingerAuth() {
        success.value= SUCCESS.FINGER_ACCESS
    }

}