package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData

class MainFragmentViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel
    //TODO FUNCIONALIDAD DEL TOTAL DEL DINERO

    val mode: MutableLiveData<String> = MutableLiveData("")

    fun add() {
        navigation.value = NAVIGATION.GO_ADD_TAP_SUCCESS
    }

}