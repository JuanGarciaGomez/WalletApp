package com.example.walletapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.walletapp.adapter.FragmentAdapter
import com.example.walletapp.data.extension_functions.toast
import com.example.walletapp.data.model.MainModel
import com.example.walletapp.data.prefs.FingerLoginOption.Companion.prefs
import com.example.walletapp.databinding.ActivityMainBinding
import com.example.walletapp.ui.viewmodel.MainViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.example.walletapp.utils.Utils.Companion.onBack
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class MainView : AppCompatActivity() {

    /**
     * This class is responsible for show four modalities
     * and apply filter according to the modality also
     * question about fingerLogin
     */

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var fragmentAdapter: FragmentAdapter
    private val fm: FragmentManager = supportFragmentManager
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModelMain = mainViewModel

        if (prefs.getFingerLogin() == 0) questionFingerPrint()

        fragmentAdapter = FragmentAdapter(fm, lifecycle)
        binding.pagerContainer.adapter = fragmentAdapter

        mainViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATION.GO_LOGOUT_VIEW -> {
                    val intent = Intent(context, LoginView::class.java)
                    context.startActivity(intent)
                    finish()
                }
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pagerContainer.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e("onTabUnselected", tab.toString())
// no se debe utilizar
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e("onTabReselected", tab.toString())
//no se debe utilizar
            }
        })

        binding.pagerContainer.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    override fun onBackPressed() {
        if (onBack == 1) {
            mainViewModel.navigation.value = NAVIGATION.GO_MAIN_VIEW
            onBack = 0
            super.onBackPressed()
        } else {
            MaterialAlertDialogBuilder(context).setTitle("Logout")
                .setMessage("¿Are you sure logout?")
                .setNegativeButton("NO") { dialog, which ->

                }
                .setPositiveButton("YES") { dialog, which ->
                    val model = MainModel()
                    model.signOut { mainViewModel.navigation.value = NAVIGATION.GO_LOGOUT_VIEW }
                }
                .show()
        }
    }

    private fun questionFingerPrint() {
        MaterialAlertDialogBuilder(context).setTitle("Fingerprint")
            .setMessage("¿Activate fingerprint login?")
            .setNegativeButton("NO") { dialog, which ->
                // Respond to negative button press
                toast("Permission denied, maybe next time")
                prefs.saveFingerLogin(2)
            }
            .setPositiveButton("YES") { dialog, which ->
                prefs.saveFingerLogin(1)
                toast("Permission granted")
            }
            .show()
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

