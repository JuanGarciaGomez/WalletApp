package com.example.walletapp.data.model

data class AddMoves(
    var mail: String = "",
    var name: String = "",
    var description: String = "",
    var amount: Double = 0.0,
    var date: String = "",
    var category: String = "",
    var move: String = "",
)