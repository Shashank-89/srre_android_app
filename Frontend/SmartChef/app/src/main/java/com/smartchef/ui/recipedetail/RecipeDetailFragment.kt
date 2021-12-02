package com.smartchef.ui.recipedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smartchef.R
import com.smartchef.databinding.OnboardingFragmentBinding
import com.smartchef.databinding.RecipeDetailFragmentBinding
import com.smartchef.model.Recipe
import com.smartchef.ui.onboarding.AppViewModel
import com.smartchef.util.DataState

class RecipeDetailFragment : Fragment(){

    private lateinit var recipe: Recipe
    private lateinit var binding: RecipeDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        val args: Bundle = requireArguments()
        val pos: Int = args.getInt("pos")

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

        super.onViewCreated(view, savedInstanceState)
    }


    private fun setupUI(recipe: Recipe) {
        binding.title.text = recipe.name
        binding.description.text = recipe.description
        val res = binding.title.resources
        binding.cal.text = res.getString(R.string.cal_q, recipe.cal.toInt())
        binding.protein.text = res.getString(R.string.protein_q, recipe.protein.toInt())
        binding.fat.text = res.getString(R.string.fat_q, recipe.totalFat.toInt())
        binding.sugar.text = res.getString(R.string.sugar_q, recipe.sugar.toInt())

        Glide.with(binding.image)
            .load(recipe.imgUrl).transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.image)

//        val ingDesc = recipe.ingredients.takeWhile { it != null }.joinToString("\n\n")

        val ingDesc = recipe.ingredients.joinToString("\n\n")
//        val steps = recipe.rawSteps.replace(".", ".\n\n")
//            .replace(" ", "")

        recipe.tags.onEach {
            if(it.isNotEmpty())binding.tagholder.addTag(it)
        }

        binding.ingredientDesc.text = ingDesc
        binding.instructionDesc.text = recipe.rawSteps
    }
}