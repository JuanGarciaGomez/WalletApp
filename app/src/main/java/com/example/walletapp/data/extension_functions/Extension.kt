package com.example.walletapp.data.extension_functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.walletapp.data.prefs.FingerLoginOption
import com.example.walletapp.utils.Utils.Companion.onBack
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

fun dialog(
    context: Context, toastPositive: String?,
    toastNegative: String?, title: String, message: String, activity: Activity,
) {
    MaterialAlertDialogBuilder(context).setTitle(title)
        .setMessage(message)
        .setNegativeButton("NO") { dialog, which ->
            // Respond to negative button press
            if (toastNegative != null) {
                FingerLoginOption.prefs.saveFingerLogin(2)
                activity.toast(toastNegative)
            }
        }
        .setPositiveButton("YES") { dialog, which ->
            if (toastPositive != null) {
                activity.toast(toastPositive)
                FingerLoginOption.prefs.saveFingerLogin(1)
            } else onBack = 2
        }
        .show()
}

