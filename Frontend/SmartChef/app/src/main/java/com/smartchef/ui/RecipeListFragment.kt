package com.smartchef.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.smartchef.R
import com.smartchef.databinding.RecipeListFragmentBinding
import com.smartchef.databinding.SearchFragmentBinding
import com.smartchef.ui.onboarding.AppViewModel
import com.smartchef.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment(){

    private lateinit var binding: RecipeListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        appViewModel.recipeList.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Error -> {

                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("resultLog", "data : size : " + dataState.data.size )
                    val gson = Gson()
                    val recipeJson = gson.toJson(dataState.data[0]).toString()
                    Log.e("resultLog", "sample : " + recipeJson)
                }
            }
        })

        appViewModel.searchParam.value?.let { appViewModel.searchRecipes(it) }
    }
}