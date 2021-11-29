package com.smartchef.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.smartchef.R
import com.smartchef.databinding.OnboardingFragmentBinding
import com.smartchef.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment(){

    private lateinit var binding: OnboardingFragmentBinding

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when(position){
                0 -> {
                    binding.next.setImageResource(R.drawable.ic_navigate_next_24)
                }
                1 -> {
                    binding.next.setImageResource(R.drawable.ic_check_24)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        binding.vp2.adapter = OnboardingAdapter(this, 2, true)
        binding.vp2.isUserInputEnabled = false //preventing swipe gesture
        binding.vp2.registerOnPageChangeCallback(onPageChangeCallback)

        binding.next.setOnClickListener(View.OnClickListener {
            if(binding.vp2.currentItem == 0){
                //ingredients
                val page = childFragmentManager.findFragmentByTag("f" + binding.vp2.currentItem) as OnBoardingPage
                appViewModel.setIngredientsParam(page.getSelections(), page.getExclusions())
                binding.vp2.setCurrentItem(1, true)
            }else{
                //tags
                val page = childFragmentManager.findFragmentByTag("f" + binding.vp2.currentItem) as OnBoardingPage
                appViewModel.setTagsParam(page.getSelections(), page.getExclusions())
                findNavController().navigate(R.id.action_onboarding_to_search)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.vp2.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

}