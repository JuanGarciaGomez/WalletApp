package com.example.walletapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.walletapp.data.model.ExpensesModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddViewModel : BaseViewModel() {

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
                    ExpensesModel(
                        Firebase.auth.currentUser?.email.toString(),
                        nameAdd,
                        descriptionAdd,
                        amountAdd.toDouble(),
                        dateAdd,
                        category
                    )
                expenses.addExpenses({
                    navigation.value = NAVIGATION.GO_MAIN_VIEW
                    success.value = SUCCESS.ADD_SUCCESS
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