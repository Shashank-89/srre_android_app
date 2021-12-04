package com.smartchef.ui.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.smartchef.databinding.RecipeDetailSheetBinding
import com.smartchef.model.Recipe
import com.smartchef.ui.common.AppViewModel
import com.smartchef.util.DataState

class RecipeDetailSheet : BottomSheetDialogFragment(){

    private lateinit var recipe: Recipe

    val array = arrayOf(
        "Directions",
        "Ingredients"
    )

    private lateinit var binding: RecipeDetailSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeDetailSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        val args: Bundle = requireArguments()
        val pos: Int = args.getInt("pos")
        val tabPos = args.getInt("tab_pos")

        appViewModel.recipeList.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Error -> {}
                is DataState.Loading -> {}
                is DataState.Success -> {
                    recipe = dataState.data[pos]
                    setupUI(recipe)
                }
            }
        })
    }

    private fun setupUI(recipe: Recipe) {

        val viewPager = binding.recipeVp2
        val tabLayout = binding.tabLayout

        val adapter = RecipeDetailVPAdapter(this, 2)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = array[position]
        }.attach()

    }
}