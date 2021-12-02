package com.smartchef.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.*
import com.smartchef.R
import com.smartchef.databinding.SearchFragmentBinding
import com.smartchef.model.SearchParam
import com.smartchef.ui.auth.AuthActivity
import com.smartchef.ui.onboarding.AppViewModel
import com.smartchef.model.Recipe
import com.google.gson.annotations.SerializedName
import com.smartchef.model.Profile
import com.smartchef.ui.auth.AuthViewModel

class SearchFragment : Fragment(R.layout.search_fragment){

    private lateinit var binding:SearchFragmentBinding

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
        binding.logout.setOnClickListener{
            val act : AuthActivity = activity as AuthActivity
            act.signOut()
        }

        val searchParam = SearchParam(ingredients = listOf("low-fat biscuit mix", "ginger juice"),
            tags = listOf("potluck", "bananas", "eggplant"))

        val gson = Gson()
        val jsonString = activity?.assets?.open("response.json")?.bufferedReader()?.readText()

        val jo: JsonObject = JsonParser().parse(jsonString).asJsonObject
        val neighbours: JsonArray = jo.getAsJsonArray("neighbors")
        val recipes: MutableList<Recipe> = mutableListOf()
        neighbours.onEach {
            recipes.add(gson.fromJson(it.asJsonArray[0], Recipe::class.java))
        }
        Log.e("SFLog", "recipes size : " + recipes.size)
        val profile = Profile("1", "default", searchParam)
        val search = gson.toJson(profile).toString()
        binding.title.setText(search)

    }
}