package com.smartchef.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.lujun.androidtagview.TagView
import com.smartchef.databinding.OnBoardingPageBinding
import com.smartchef.ui.SelectionsInterface
import com.smartchef.ui.TISearchAdapter
import com.smartchef.ui.auth.AuthViewModel
import com.smartchef.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingPage : Fragment(), SelectionsInterface{

    companion object {
        private const val ARG_POSITION = "pos"
        private const val ONBOARDING   = "ob"

        fun getInstance(position: Int, onboarding: Boolean) = OnBoardingPage().apply {
            arguments = bundleOf(ARG_POSITION to position,
                ONBOARDING to onboarding)
        }
    }

    private var position = 0
    private var onboarding = false
    private lateinit var appViewModel: AppViewModel
    private lateinit var adapter: TISearchAdapter
    private lateinit var binding: OnBoardingPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = OnBoardingPageBinding.inflate(inflater, container, false)
        val args: Bundle = requireArguments()
        position = args.getInt(ARG_POSITION)
        onboarding = args.getBoolean(ONBOARDING)

        adapter = TISearchAdapter()
        val manager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding.grid.layoutManager = manager
        binding.grid.adapter = adapter

        binding.included.setOnTagClickListener(object:TagView.OnTagClickListener{
            override fun onTagClick(pos: Int, tag: String) {
                binding.included.removeTag(pos)
                adapter.selections[tag] = false
                adapter.notifyDataSetChanged()
            }

            override fun onTagLongClick(p0: Int, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onSelectedTagDrag(p0: Int, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onTagCrossClick(p0: Int) {
                TODO("Not yet implemented")
            }
        })

        binding.excluded.setOnTagClickListener(object:TagView.OnTagClickListener{
            override fun onTagClick(pos: Int, tag: String) {
                binding.excluded.removeTag(pos)
                adapter.exclusions[tag] = false
                adapter.notifyDataSetChanged()
            }

            override fun onTagLongClick(p0: Int, p1: String) {
                TODO("Not yet implemented")
            }

            override fun onSelectedTagDrag(p0: Int, p1: String) {
                TODO("Not yet implemented")
            }

            override fun onTagCrossClick(p0: Int) {
                TODO("Not yet implemented")
            }

        })

        adapter.clickListener = object : TISearchAdapter.OnItemClick{
            override fun onItemClicked(data: String, selected: Boolean, excluded: Boolean) {
                if(selected) {
                    binding.included.addTag(data)
                }else if(excluded){
                    binding.excluded.addTag(data)
                }else{
                    var index = binding.included.tags.indexOf(data)
                    if(index > -1) binding.included.removeTag(index)
                    index = binding.excluded.tags.indexOf(data)
                    if(index > -1) binding.excluded.removeTag(index)
                }
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        when(position){
            0 -> {
                appViewModel.dataStateIngredients.observe(viewLifecycleOwner, { dataState ->
                    when(dataState){
                        is DataState.Error -> TODO()
                        is DataState.Loading -> TODO()
                        is DataState.Success -> {
                            adapter.data = dataState.data
                        }
                    }
                })

                appViewModel.selectedIngredients.observe(viewLifecycleOwner, { dataState ->
                    when(dataState){
                        is DataState.Error -> TODO()
                        is DataState.Loading -> TODO()
                        is DataState.Success -> {
                            adapter.selections = dataState.data
                        }
                    }
                })
            }

            1 -> {

                if(onboarding){
//                    appViewModel.dataStateCuisines.observe(viewLifecycleOwner, { dataState ->
//                        when(dataState){
//                            is DataState.Error -> TODO()
//                            is DataState.Loading -> TODO()
//                            is DataState.Success -> {
//                                adapter.data = dataState.data
//                            }
//                        }
//                    })

                    appViewModel.dataStateTags.observe(viewLifecycleOwner, { dataState ->
                        when(dataState){
                            is DataState.Error -> TODO()
                            is DataState.Loading -> TODO()
                            is DataState.Success -> {
                                adapter.data = dataState.data
                            }
                        }
                    })

                    appViewModel.selectedCuisines.observe(viewLifecycleOwner, { dataState ->
                        when(dataState){
                            is DataState.Error -> TODO()
                            is DataState.Loading -> TODO()
                            is DataState.Success -> {
                                adapter.selections = dataState.data
                            }
                        }
                    })
                }else{
                    appViewModel.dataStateTags.observe(viewLifecycleOwner, { dataState ->
                        when(dataState){
                            is DataState.Error -> TODO()
                            is DataState.Loading -> TODO()
                            is DataState.Success -> {
                                adapter.data = dataState.data
                            }
                        }
                    })

                    appViewModel.selectedTags.observe(viewLifecycleOwner, { dataState ->
                        when(dataState){
                            is DataState.Error -> TODO()
                            is DataState.Loading -> TODO()
                            is DataState.Success -> {
                                adapter.selections = dataState.data
                            }
                        }
                    })
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        when(position){
            0 -> {
                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetIngredients)
                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetIngredientSelections)
            }
            1 -> {
                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetTags)
//                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetSelectedTags)
            }
        }
    }

    override fun getSelections(): HashMap<String, Boolean> {
        return adapter?.selections
    }


    override fun getExclusions(): HashMap<String, Boolean> {
        return adapter?.exclusions
    }

}