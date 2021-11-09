package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.walletapp.databinding.ActivityLoginBinding
import com.example.walletapp.ui.viewmodel.ERRORES
import com.example.walletapp.ui.viewmodel.LoginViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATIONS
import com.example.walletapp.ui.viewmodel.SUCCESS
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.Executor

class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    private val context = this

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModelLogin = loginViewModel

        loginViewModel.success.observe(this, {
            when (it) {
                SUCCESS.LOGIN_SUCCESS -> {
                    Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show()
                }
                SUCCESS.FINGER_ACCESS -> {
                    fingerLogin()
                    biometricPrompt.authenticate(promptInfo)
                }
            }
        })
        loginViewModel.error.observe(this, {
            when (it) {
                ERRORES.EMPTY_FIELDS -> {
                    Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show()
                }
                ERRORES.WRONG_CREDENTIALS -> {
                    Toast.makeText(this, "Error de credenciales", Toast.LENGTH_SHORT).show()
                }
            }
        })
        loginViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATIONS.GO_REGISTER_VIEW -> {
                    val intent = Intent(context, RegisterView::class.java)
                    context.startActivity(intent)
                }
                NAVIGATIONS.GO_MAIN_VIEW -> {
                    val intent = Intent(context, MainView::class.java)
                    context.startActivity(intent)
                    finish()
                }
            }
        })

    }

    private fun fingerLogin() {

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
    }

}