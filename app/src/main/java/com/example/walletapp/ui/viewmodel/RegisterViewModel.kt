package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.RegisterModel

class RegisterViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val passwordConfirm: MutableLiveData<String> = MutableLiveData("")

    fun register() {
        val emailRegister = email.value ?: ""
        val passwordRegister = password.value ?: ""
        val passwordConfirmRegister = password.value ?: ""
        try {
            if (emailRegister.isEmpty()
                || passwordRegister.isEmpty()
                || passwordConfirmRegister.isEmpty()
            ) error.value = ERRORES.EMPTY_FIELDS
           else {
                val model = RegisterModel(emailRegister, passwordRegister, passwordConfirmRegister)
                model.register({
                    navigation.value = NAVIGATIONS.GO_MAIN_VIEW
                }, {
                    error.value = ERRORES.WRONG_CREDENTIALS
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}