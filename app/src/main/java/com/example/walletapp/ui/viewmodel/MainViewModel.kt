package com.example.walletapp.ui.viewmodel

class MainViewModel : BaseViewModel() {

    fun add() {
        navigation.value = NAVIGATION.GO_ADD_TAP_SUCCESS
    }


}