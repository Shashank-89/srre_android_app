package com.smartchef.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.smartchef.R
import com.smartchef.databinding.OnboardingFragmentBinding

class OnboardingFragment : Fragment(){

    private lateinit var binding: OnboardingFragmentBinding
    private val viewModel: OBViewModel by viewModels()

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when(position){
                0 -> binding.next.setImageResource(R.drawable.ic_navigate_next_24)
                1 -> binding.next.setImageResource(R.drawable.ic_check_24)
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
        binding.vp2.adapter = OnboardingAdapter(this, 2)
        binding.vp2.isUserInputEnabled = false //preventing swipe gesture
        binding.next.setOnClickListener(View.OnClickListener {
            if(binding.vp2.currentItem == 0){
                binding.vp2.setCurrentItem(1, true)
            }else{

            }
        })
    }
}