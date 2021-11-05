package com.example.walletapp.data.model

import com.example.walletapp.data.network.FireBaseController

data class RegisterModel(val email: String, val password: String, val passwordConfirm: String) {
    private val firebaseController = FireBaseController()

    fun auth(success: () -> Unit, error: (String) -> Unit) {
        //firebaseController.auth(this, success, error)
    }
}