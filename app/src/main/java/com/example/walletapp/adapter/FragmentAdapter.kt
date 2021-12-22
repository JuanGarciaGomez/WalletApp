package com.example.walletapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.walletapp.ui.view.fragment.MainFragment
import com.example.walletapp.ui.view.fragment.MainFragment.Companion.newInstance

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                newInstance()
            }
            1 -> {
                MainFragment()
            }
            2 -> {
                MainFragment()
            }
            3 -> {
                MainFragment()
            }
            else -> {
                MainFragment()
            }
        }

    }

    override fun getItemCount(): Int {
        return 4
    }


}