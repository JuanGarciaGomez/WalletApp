package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.walletapp.data.extension_functions.toast
import com.example.walletapp.databinding.ActivityRegisterBinding
import com.example.walletapp.ui.viewmodel.ERROR
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.example.walletapp.ui.viewmodel.RegisterViewModel
import com.example.walletapp.ui.viewmodel.SUCCESS

class RegisterView : AppCompatActivity() {

    /**
     * This class is responsible for show register form
     */

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModelRegister = registerViewModel

        registerViewModel.success.observe(this, {
            when (it) {
                SUCCESS.REGISTER_SUCCESS -> {
                    toast("Correct Registration")
                }
            }
        })

        registerViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATION.GO_MAIN_VIEW -> {
                    val intent = Intent(context, MainView::class.java)
                    context.startActivity(intent)
                }
            }
        })

        registerViewModel.error.observe(this, {
            when (it) {
                ERROR.EMPTY_FIELDS -> {
                    toast("Empty fields")
                }
                ERROR.ERROR_PASSWORD -> {
                    toast("Passwords must match")
                }
            }

        })

    }
}