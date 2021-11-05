package com.example.walletapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.walletapp.R
import com.example.walletapp.data.prefs.FingerLoginOption.Companion.prefs
import com.example.walletapp.databinding.ActivityLoginBinding
import com.example.walletapp.databinding.ActivityMainBinding
import com.example.walletapp.ui.viewmodel.LoginViewModel
import com.example.walletapp.ui.viewmodel.MainViewModel

class MainView : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val MainViewModel: MainViewModel by viewModels()

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}