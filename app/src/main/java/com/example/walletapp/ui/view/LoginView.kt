package com.example.walletapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.walletapp.data.extension_functions.intentTo
import com.example.walletapp.data.extension_functions.toast
import com.example.walletapp.data.model.LoginModel
import com.example.walletapp.data.prefs.FingerLoginOption.Companion.prefs
import com.example.walletapp.databinding.ActivityLoginBinding
import com.example.walletapp.ui.viewmodel.ERROR
import com.example.walletapp.ui.viewmodel.LoginViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.example.walletapp.ui.viewmodel.SUCCESS
import com.example.walletapp.utils.Utils.Companion.decryptPass
import com.example.walletapp.utils.Utils.Companion.decryptUser
import com.example.walletapp.utils.Utils.Companion.passKey
import com.example.walletapp.utils.Utils.Companion.userKey
import java.util.concurrent.Executor

class LoginView : AppCompatActivity() {

    /**
     * This class is responsible to show the login
     * and use fingerLogin
     */

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
                    toast("Login correcto")
                }
                SUCCESS.FINGER_ACCESS -> {
                    fingerLogin()
                }
            }
        })
        loginViewModel.error.observe(this, {
            when (it) {
                ERROR.EMPTY_FIELDS -> {
                    toast("Campos vacios")
                }
                ERROR.WRONG_CREDENTIALS -> {
                    toast("Error de credenciales")
                }
            }
        })
        loginViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATION.GO_REGISTER_VIEW -> {
                    intentTo(RegisterView::class.java)
                }
                NAVIGATION.GO_MAIN_VIEW -> {
                    intentTo(MainView::class.java)
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
                    toast("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    toast("Authentication suceeded!")

                    val model = LoginModel(decryptUser(prefs.getUsersLogin(), userKey),
                        decryptPass(prefs.getKeyLogin(),
                            passKey))
                    model.auth({
                        loginViewModel.navigation.value = NAVIGATION.GO_MAIN_VIEW
                        loginViewModel.success.value = SUCCESS.LOGIN_SUCCESS
                        //   prefs.saveFingerLogin(true)
                    }, {
                        loginViewModel.error.value = ERROR.WRONG_CREDENTIALS
                    })

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    toast("Authentication failed!")
                }
            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

}