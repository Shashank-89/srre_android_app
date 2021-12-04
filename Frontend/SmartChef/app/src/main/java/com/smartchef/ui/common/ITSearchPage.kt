package com.smartchef.ui.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.lujun.androidtagview.TagView
import com.smartchef.databinding.OnBoardingPageBinding
import com.smartchef.model.SearchParam
import com.smartchef.ui.SelectionsInterface
import com.smartchef.ui.TISearchAdapter
import com.smartchef.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ITSearchPage : Fragment(), SelectionsInterface{

    companion object {
        private const val ARG_POSITION = "pos"
        private const val ONBOARDING   = "ob"

        fun getInstance(position: Int, onboarding: Boolean) = ITSearchPage().apply {
            arguments = bundleOf(
                ARG_POSITION to position,
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

        Log.e("OBFragLog", "onCreateView on page called!!")
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
        Log.e("OBFragLog", "onViewCreated on page called!!")
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        when(position){
            0 -> {
                appViewModel.dataStateIngredients.observe(viewLifecycleOwner, { dataState ->
                    when(dataState){
                        is DataState.Error -> TODO()
                        is DataState.Loading ->{
                            binding.progress.show()
                        }
                        is DataState.Success -> {
                            binding.progress.hide()
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

                appViewModel.dataStateTags.observe(viewLifecycleOwner, { dataState ->
                    when(dataState){
                        is DataState.Error -> TODO()
                        is DataState.Loading -> TODO()
                        is DataState.Success -> {
                            adapter.data = dataState.data
                        }
                    }
                })

            }
        }

        val param: SearchParam? = appViewModel.searchParam.value
        if(param != null){
            when(position){
                0 -> {
                    val ingredExclusionsMap = hashMapOf<String, Boolean>()
                    val ingredientsMap = hashMapOf<String, Boolean>()

                    param.ingredients.forEach {
                        ingredientsMap[it] = true
                        binding.included.addTag(it)
                    }

                    param.ingredientExclusions.forEach{
                        ingredExclusionsMap[it] = true
                        binding.excluded.addTag(it)
                    }

                    adapter.selections = ingredientsMap
                    adapter.exclusions = ingredExclusionsMap
                }

                1 -> {
                    val tagExclusionsMap = hashMapOf<String, Boolean>()
                    val tagMap = hashMapOf<String, Boolean>()

                    param.tags.forEach {
                        tagMap[it] = true
                        binding.included.addTag(it)
                    }

                    param.tagExclusions.forEach{
                        tagExclusionsMap[it] = true
                        binding.excluded.addTag(it)
                    }

                    adapter.selections = tagMap
                    adapter.exclusions = tagExclusionsMap
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        when(position){
            0 -> {
                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetIngredients)
            }
            1 -> {
                appViewModel.setStateEvent(AppViewModel.QueryEvent.GetTags)
            }
        }
        Log.e("OBFragLog", "onStart on page called!!")
    }

    override fun onResume() {
        super.onResume()
        adapter?.filter.filter("")
        Log.e("OBFragLog", "onResume on page called!!")
    }

    override fun getSelections(): HashMap<String, Boolean> {
        return adapter?.selections
    }


    override fun getExclusions(): HashMap<String, Boolean> {
        return adapter?.exclusions
    }

}