package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.AddMovesModel
import com.example.walletapp.utils.Utils.Companion.dataUItoDB
import com.example.walletapp.utils.Utils.Companion.move
import com.example.walletapp.utils.Utils.Companion.onBack
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddViewModel : BaseViewModel() {

    /**
     * This class is responsible for connect view with model
     * also this class can do validations
     * Connect AddFragment.kt with fragment_add.xml
     */

    val name: MutableLiveData<String> = MutableLiveData("")
    val amount: MutableLiveData<String> = MutableLiveData("")
    val date: MutableLiveData<String> = MutableLiveData("")
    val description: MutableLiveData<String> = MutableLiveData("")
    val category: MutableLiveData<String> = MutableLiveData("")

    fun cancel() {
        success.value = SUCCESS.CLOSE_SUCCESS
    }

    fun accept() {

        val nameAdd = name.value ?: ""
        val amountAdd = amount.value ?: ""
        val dateAdd = date.value ?: ""
        val descriptionAdd = description.value ?: ""
        val category = category.value ?: ""

        try {
            if (amountAdd.isEmpty() || dateAdd.isEmpty() || descriptionAdd.isEmpty() || nameAdd.isEmpty() || category.isEmpty())
                error.value = ERROR.EMPTY_FIELDS
            else {
                val expenses =
                    AddMovesModel(
                        Firebase.auth.currentUser?.email.toString(),
                        nameAdd,
                        descriptionAdd,
                        amountAdd.toDouble(),
                        dataUItoDB(dateAdd),
                        category,
                        move
                    )
                expenses.addExpenses({
                    navigation.value = NAVIGATION.GO_MAIN_VIEW
                    success.value = SUCCESS.ADD_SUCCESS
                    onBack = 0
                }, {
                    error.value = ERROR.ERROR_ADD_EXPENSES
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun datePicker() {
        success.value = SUCCESS.DATE_PICKER
    }
}