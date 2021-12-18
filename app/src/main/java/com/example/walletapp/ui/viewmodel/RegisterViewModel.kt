package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.RegisterModel
import com.example.walletapp.data.prefs.FingerLoginOption

class RegisterViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val passwordConfirm: MutableLiveData<String> = MutableLiveData("")

    fun register() {
        val emailRegister = email.value ?: ""
        val passwordRegister = password.value ?: ""
        val passwordConfirmRegister = passwordConfirm.value ?: ""
        try {
            if (emailRegister.isEmpty()
                || passwordRegister.isEmpty()
                || passwordConfirmRegister.isEmpty()
            ) error.value = ERROR.EMPTY_FIELDS
            else if(passwordRegister != passwordConfirmRegister){
                error.value = ERROR.ERROR_PASSWORD
            }else{
                val model = RegisterModel(emailRegister, passwordRegister, passwordConfirmRegister)
                model.register({
                    navigation.value = NAVIGATION.GO_MAIN_VIEW
                    success.value = SUCCESS.REGISTER_SUCCESS
                    FingerLoginOption.prefs.saveCredentialsLogin(emailRegister, passwordRegister)
                }, {
                    error.value = ERROR.WRONG_CREDENTIALS
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}