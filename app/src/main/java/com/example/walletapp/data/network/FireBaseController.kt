package com.example.walletapp.data.network

import com.example.walletapp.data.model.AddMoves
import com.example.walletapp.data.model.AddMovesModel
import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.model.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FireBaseController {

    private var instance = FirebaseAuth.getInstance()

    private val db = Firebase.firestore
    private val collection = db.collection("addMoves")


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

    /**
     * add moves in fireStore
     */
    fun addMoves(addMovesModel: AddMovesModel, success: () -> Unit, error: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val moves = AddMoves(
                mail = addMovesModel.mail,
                name = addMovesModel.name,
                description = addMovesModel.description,
                amount = addMovesModel.amount,
                date = addMovesModel.date,
                category = addMovesModel.category,
                move = addMovesModel.move,
            )
            collection.add(moves).addOnSuccessListener {
                success.invoke()
            }.addOnFailureListener { e ->
                e.printStackTrace()
                error.invoke()
            }
        }
    }

}