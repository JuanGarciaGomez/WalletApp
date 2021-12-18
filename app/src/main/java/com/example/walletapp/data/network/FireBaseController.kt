package com.example.walletapp.data.network

import com.example.walletapp.data.model.Expenses
import com.example.walletapp.data.model.ExpensesModel
import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.model.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FireBaseController {

    private var instance = FirebaseAuth.getInstance()

    private val db = Firebase.firestore
    private val collection = db.collection("expenses")


    fun auth(model: LoginModel, success: () -> Unit, error: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            instance.signInWithEmailAndPassword(model.email, model.password).addOnCompleteListener {
                if (it.isSuccessful) {
                    success.invoke()
                } else {
                    error.invoke("xD")
                }
            }
        }
    }

    fun register(model: RegisterModel, success: () -> Unit, error: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            instance.createUserWithEmailAndPassword(model.email, model.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        success.invoke()
                    } else {
                        error.invoke("xD")
                    }
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

    fun addExpenses(expensesModel: ExpensesModel, success: () -> Unit, error: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val expenses = Expenses(
                mail = expensesModel.mail,
                name = expensesModel.name,
                description = expensesModel.description,
                amount = expensesModel.amount,
                date = expensesModel.date,
                category = expensesModel.category
            )
            collection.add(expenses).addOnSuccessListener {
                success.invoke()
            }.addOnFailureListener { e ->
                e.printStackTrace()
                error.invoke()
            }
        }
    }

}