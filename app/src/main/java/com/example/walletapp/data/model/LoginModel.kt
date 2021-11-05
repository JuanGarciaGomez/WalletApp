package com.example.walletapp.data.model

import com.example.walletapp.data.network.FireBaseController

data class LoginModel(val email: String, val password: String) {
    private val firebaseController = FireBaseController()

    fun auth(success: () -> Unit, error: (String) -> Unit) {
        firebaseController.auth(this, success, error)
    }
}