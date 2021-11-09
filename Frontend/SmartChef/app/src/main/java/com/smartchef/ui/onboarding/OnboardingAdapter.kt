package com.smartchef.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragment:Fragment, private val itemCount: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardingPage.getInstance(position)
    }

}