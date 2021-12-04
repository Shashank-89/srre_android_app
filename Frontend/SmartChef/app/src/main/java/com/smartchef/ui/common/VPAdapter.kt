package com.smartchef.ui.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter(
    fragment:Fragment,
    private val itemCount: Int,
    private val onboarding: Boolean) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        return ITSearchPage.getInstance(position, onboarding)
    }



}