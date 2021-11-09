package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletapp.data.network.FireBaseController

//TODO fix plural to singular
open class BaseViewModel : ViewModel() {
    val error : MutableLiveData<ERRORES> = MutableLiveData(null)
    val success : MutableLiveData<SUCCESS> = MutableLiveData(null)
    val navigation : MutableLiveData<NAVIGATIONS> = MutableLiveData(null)
    val firebaseController = FireBaseController()
}

enum class ERRORES{
    EMPTY_FIELDS,
    WRONG_CREDENTIALS,
    NO_ERROR

}

enum class SUCCESS{
    LOGIN_SUCCESS,
    FINGER_ACCESS,
    ADD_SUCCESS
}

enum class NAVIGATIONS{
    GO_MAIN_VIEW,
    GO_REGISTER_VIEW,
    GO_LOGIN_VIEW,
    GO_LOGOUT_VIEW
}