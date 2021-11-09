package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.walletapp.R
import com.example.walletapp.adapter.FragmentAdapter
import com.example.walletapp.data.model.MainModel
import com.example.walletapp.databinding.ActivityMainBinding
import com.example.walletapp.ui.viewmodel.MainViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATIONS
import com.example.walletapp.ui.viewmodel.SUCCESS
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class MainView : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var tableLayout: TabLayout
    private lateinit var pager2: ViewPager2
    private val fm: FragmentManager = supportFragmentManager
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModelMain = mainViewModel


        pager2 = findViewById(R.id.pager_container)
        tableLayout = findViewById(R.id.tab_layout)
        fragmentAdapter = FragmentAdapter(fm, lifecycle)
        pager2.adapter = fragmentAdapter

        mainViewModel.success.observe(this, {
            when (it) {
                SUCCESS.ADD_SUCCESS -> {
                    Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show()
                }
            }
        })
        mainViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATIONS.GO_LOGOUT_VIEW -> {
                    val intent = Intent(context, LoginView::class.java)
                    context.startActivity(intent)
                    finish()
                }
            }
        })

        tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tableLayout.selectTab(tableLayout.getTabAt(position))
            }
        })
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(context).setTitle("Logout")
            .setMessage("¿Are you sure logout?")
            .setNegativeButton("NO") { dialog, which ->
                // Respond to negative button press
                Log.e("NO", "no")
            }
            .setPositiveButton("YES") { dialog, which ->
                val model = MainModel()
                model.signOut { mainViewModel.navigation.value = NAVIGATIONS.GO_LOGOUT_VIEW }
            }
            .show()
    }
}
