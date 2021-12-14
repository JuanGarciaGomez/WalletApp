package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.walletapp.R
import com.example.walletapp.adapter.FragmentAdapter
import com.example.walletapp.data.model.MainModel
import com.example.walletapp.databinding.ActivityMainBinding
import com.example.walletapp.ui.fragment.AddFragment
import com.example.walletapp.ui.viewmodel.MainViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class MainView : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*  private val fileResult = 1
      private val dataBase = Firebase.database
      private val myRef = dataBase.getReference("user")*/

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

        mainViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATION.GO_LOGOUT_VIEW -> {
                    val intent = Intent(context, LoginView::class.java)
                    context.startActivity(intent)
                    finish()
                }
                NAVIGATION.GO_ADD_TAP_SUCCESS -> {
                    showDialog()
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

    private fun showDialog() {
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)
        val fragmentManager = supportFragmentManager
        val newFragment = AddFragment()
        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        Log.e("onBack", mainViewModel.navigation.value.toString())
        if (mainViewModel.navigation.value == NAVIGATION.GO_ADD_TAP_SUCCESS) {
            mainViewModel.navigation.value = NAVIGATION.GO_MAIN_VIEW
            super.onBackPressed()
        } else {
            MaterialAlertDialogBuilder(context).setTitle("Logout")
                .setMessage("¿Are you sure logout?")
                .setNegativeButton("NO") { dialog, which ->
                    // Respond to negative button press
                    Log.e("NO", "no")
                }
                .setPositiveButton("YES") { dialog, which ->
                    val model = MainModel()
                    model.signOut { mainViewModel.navigation.value = NAVIGATION.GO_LOGOUT_VIEW }
                }
                .show()
        }
    }

    /*   //Abrir camara
       fun fileManager() {
           val intent = Intent(Intent.ACTION_GET_CONTENT)
           intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
           intent.type = ""
           startActivityForResult(intent, fileResult)
       }*/

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null) {

                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }

                } else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }

                }
            }

        }
    }*/

/*    private fun fileUpload(it: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("User")
        val path = it.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')))


        fileName.putFile(it).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = uri.toString()
                myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.e(null, "SUCCESSFUL")

            }

        }.addOnFailureListener {
            Log.e(null, "WRONG")
        }

    }*/

}

