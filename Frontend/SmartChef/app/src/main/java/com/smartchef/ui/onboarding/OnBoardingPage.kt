package com.smartchef.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.smartchef.databinding.OnBoardingPageBinding

class OnBoardingPage : Fragment(){

    companion object {
        private const val ARG_POSITION = "pos"

        fun getInstance(position: Int) = OnBoardingPage().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }

    private lateinit var binding: OnBoardingPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = OnBoardingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

}