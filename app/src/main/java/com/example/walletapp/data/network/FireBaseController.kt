package com.example.walletapp.data.network

import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.model.RegisterModel
import com.example.walletapp.ui.viewmodel.NAVIGATIONS
import com.google.firebase.auth.FirebaseAuth

class FireBaseController {

    private var instance: FirebaseAuth = FirebaseAuth.getInstance()


    fun auth(model: LoginModel, success: () -> Unit, error: (String) -> Unit) {
        instance.signInWithEmailAndPassword(model.email, model.password).addOnCompleteListener {
            if (it.isSuccessful) {
                success.invoke()
            } else {
                error.invoke("xD")
            }
        }
    }

    fun register(model: RegisterModel, success: () -> Unit, error: (String) -> Unit) {
        instance.createUserWithEmailAndPassword(model.email, model.password).addOnCompleteListener {
            if (it.isSuccessful) {
                success.invoke()
            } else {
                error.invoke("xD")
            }
        }
    }

    fun hasSession(): Boolean {
        return instance.currentUser != null
    }

    fun signOut(success: () -> Unit) {
        instance.signOut()
        success.invoke()
    }
}