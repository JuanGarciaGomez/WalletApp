package com.example.walletapp.data.model

import com.example.walletapp.data.network.FireBaseController

class MainModel {

    private val firebaseController = FireBaseController()

    fun signOut(success: () -> Unit){
        firebaseController.signOut(success)
    }

}