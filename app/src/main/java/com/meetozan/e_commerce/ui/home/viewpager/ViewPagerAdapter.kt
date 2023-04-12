package com.meetozan.e_commerce.ui.home.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meetozan.e_commerce.ui.man.ManFragment
import com.meetozan.e_commerce.ui.newest.NewestFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NewestFragment()
        1 -> ManFragment()
        else -> NewestFragment()
    }
}