package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.walletapp.databinding.ActivityRegisterBinding
import com.example.walletapp.ui.viewmodel.ERRORES
import com.example.walletapp.ui.viewmodel.NAVIGATIONS
import com.example.walletapp.ui.viewmodel.RegisterViewModel
import com.example.walletapp.ui.viewmodel.SUCCESS

class RegisterView : AppCompatActivity() {

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
                    Toast.makeText(this, "Registro correcto", Toast.LENGTH_SHORT).show()
                }
            }
        })

        registerViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATIONS.GO_MAIN_VIEW -> {
                    val intent = Intent(context, MainView::class.java)
                    context.startActivity(intent)
                }
            }
        })

        registerViewModel.error.observe(this, {
            when (it) {
                ERRORES.EMPTY_FIELDS -> {
                    Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}