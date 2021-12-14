package com.example.walletapp.data.extension_functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.DialogFragment

fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun DialogFragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, length).show()
}

fun Context.intentTo(cls: Class<*>?) {
    this.startActivity(Intent(this, cls))
}

fun DialogFragment.intentTo(cls: Class<*>?) {
    requireContext().startActivity(Intent(requireContext(), cls))
}

