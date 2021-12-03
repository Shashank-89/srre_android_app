package com.smartchef.ui.recipedetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class RecipeDetailVPAdapter(
    fragment: Fragment,
    private val itemCount: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        val steps = listOf<String>()
        return RecipeDetailPage.getInstance(position, steps)
    //        return OnBoardingPage.getInstance(position, onboarding)
    }

}