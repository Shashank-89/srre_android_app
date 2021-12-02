package com.smartchef.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.smartchef.R
import com.smartchef.databinding.RecipeListFragmentBinding
import com.smartchef.databinding.SearchFragmentBinding
import com.smartchef.model.Recipe
import com.smartchef.ui.onboarding.AppViewModel
import com.smartchef.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment(){

    private lateinit var binding: RecipeListFragmentBinding
    private lateinit var adapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        adapter = RecipeListAdapter()
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.clickListener = object :RecipeListAdapter.OnItemClick{
            override fun onItemClick(recipe: Recipe, pos: Int) {

                findNavController().navigate(
                    R.id.action_list_to_detail,
                    bundleOf(
                        "pos" to pos
                    )
                )
            }

        }

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
                    adapter.data = dataState.data
                }
            }
        })

        appViewModel.searchParam.value?.let { appViewModel.searchRecipes(it) }
    }
}