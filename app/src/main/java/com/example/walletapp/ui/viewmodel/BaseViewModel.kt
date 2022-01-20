package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletapp.data.network.FireBaseController

/**
 * This open class is the base for all project because inside there are
 * variables with live data*
 */
open class BaseViewModel : ViewModel() {
    val error : MutableLiveData<ERROR> = MutableLiveData(null)
    val success : MutableLiveData<SUCCESS> = MutableLiveData(null)
    val navigation : MutableLiveData<NAVIGATION> = MutableLiveData(null)
    val update : MutableLiveData<UPDATE> = MutableLiveData(null)
    val firebaseController = FireBaseController()
}

enum class ERROR{
    EMPTY_FIELDS,
    WRONG_CREDENTIALS,
    ERROR_ADD_EXPENSES,
    ERROR_PASSWORD

}

enum class SUCCESS{
    LOGIN_SUCCESS,
    FINGER_ACCESS,
    REGISTER_SUCCESS,
    CLOSE_SUCCESS,
    DATE_PICKER,
    ADD_SUCCESS
}

enum class NAVIGATION{
    GO_MAIN_VIEW,
    GO_REGISTER_VIEW,
    GO_LOGIN_VIEW,
    GO_ADD_TAP_SUCCESS,
    GO_LOGOUT_VIEW
}

enum class UPDATE{
   UPDATE_LIST
}