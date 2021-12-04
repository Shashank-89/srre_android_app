package com.smartchef.ui.recipedetail

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class RecipeDetailPage : Fragment(){

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_STEPS    = "steps"

        fun getInstance(position: Int, steps: List<String>) = RecipeDetailPage().apply {
            arguments = bundleOf(ARG_POSITION to position,
                ARG_STEPS to steps)
        }

    }

}
