package com.example.walletapp.data.model

import com.example.walletapp.data.network.FireBaseController

data class ExpensesModel(
    val mail: String,
    val name: String,
    val description: String,
    val amount: Double,
    val date: String,
    val category: String
) {
    private val firebaseController = FireBaseController()

    fun addExpenses(success: () -> Unit, error: () -> Unit) {
        firebaseController.addExpenses(this, success, error)
    }

}