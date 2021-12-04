package com.smartchef.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.smartchef.R
import com.smartchef.databinding.SearchFragmentBinding
import com.smartchef.model.Profile
import com.smartchef.ui.auth.AuthActivity
import com.smartchef.ui.common.AppViewModel
import com.smartchef.ui.common.ITSearchPage
import com.smartchef.ui.common.VPAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment){

    private lateinit var binding:SearchFragmentBinding

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when(position){
                0 -> {
                    binding.next.setImageResource(R.drawable.ic_navigate_next_24)
                    binding.title.setText(R.string.select_ingredients)
                }
                1 -> {
                    binding.next.setImageResource(R.drawable.ic_baseline_search_32)
                    binding.title.setText(R.string.select_tags)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        binding.vp2.adapter = VPAdapter(this, 2, false)
        binding.vp2.registerOnPageChangeCallback(onPageChangeCallback)

        val adapter = ProfileAdapter(view.context, R.id.profile_item)
        binding.selector.setAdapter(adapter)

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        appViewModel.database.getReference("profile/$uid").child("profile_list").get().addOnSuccessListener {
            val profile: MutableList<Profile> = it.value as MutableList<Profile>
            Log.i("firebase", "Got value ${it.value} size ${profile.size}")
            adapter.data = profile
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


        binding.logout.setOnClickListener{
            val act : AuthActivity = activity as AuthActivity
            act.signOut()
        }

        binding.next.setOnClickListener(View.OnClickListener {
            if(binding.vp2.currentItem == 0){
                //ingredients
                val page = childFragmentManager.findFragmentByTag("f" + binding.vp2.currentItem) as ITSearchPage
                appViewModel.setIngredientsParam(page.getSelections(), page.getExclusions(), true,)
                binding.vp2.setCurrentItem(1, true)
            }else{
                //tags
                val page = childFragmentManager.findFragmentByTag("f" + binding.vp2.currentItem) as ITSearchPage
                appViewModel.setTagsParam(page.getSelections(), page.getExclusions())
                binding.vp2.setCurrentItem(0, false)
                findNavController().navigate(R.id.action_landing_to_search)
            }
        })

    }
}