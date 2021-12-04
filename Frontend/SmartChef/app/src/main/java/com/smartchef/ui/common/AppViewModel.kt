package com.smartchef.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.smartchef.model.Profile
import com.smartchef.model.Recipe
import com.smartchef.model.SearchParam
import com.smartchef.repository.AppRepository
import com.smartchef.util.DataState
import com.smartchef.ui.common.AppViewModel.QueryEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val obRepo : AppRepository, val database: FirebaseDatabase): ViewModel() {

    private val _dataStateIngredients: MutableLiveData<DataState<List<String>>> = MutableLiveData()

    val dataStateIngredients: LiveData<DataState<List<String>>>
        get() = _dataStateIngredients

    private val _dataStateSelectedIngredients: MutableLiveData<DataState<HashMap<String, Boolean>>> = MutableLiveData()

    val selectedIngredients: LiveData<DataState<HashMap<String, Boolean>>>
        get() = _dataStateSelectedIngredients

    private val _dataStateTags: MutableLiveData<DataState<List<String>>> = MutableLiveData()

    val dataStateTags: LiveData<DataState<List<String>>>
        get() = _dataStateTags

    private val _dataStateSelectedTags: MutableLiveData<DataState<HashMap<String, Boolean>>> = MutableLiveData()

    val selectedTags: LiveData<DataState<HashMap<String, Boolean>>>
        get() = _dataStateSelectedTags

    private val _dataStateCuisines: MutableLiveData<DataState<List<String>>> = MutableLiveData()

    val dataStateCuisines: LiveData<DataState<List<String>>>
        get() = _dataStateCuisines

    private val _dataStateSelectedCuisines: MutableLiveData<DataState<HashMap<String, Boolean>>> = MutableLiveData()

    val selectedCuisines: LiveData<DataState<HashMap<String, Boolean>>>
        get() = _dataStateSelectedCuisines

    private val _dataStateRecipes: MutableLiveData<DataState<List<Recipe>>> = MutableLiveData()

    val recipeList: LiveData<DataState<List<Recipe>>>
        get() = _dataStateRecipes

    private val _searchParam: MutableLiveData<SearchParam> = MutableLiveData()
    val searchParam : LiveData<SearchParam>
        get() = _searchParam

    private val _profile: MutableLiveData<Profile> = MutableLiveData()
    val profile : LiveData<Profile>
        get() = _profile

    fun setStateEvent(queryEvent: QueryEvent){
        viewModelScope.launch {
            when(queryEvent){
                is GetIngredients -> {
                    obRepo.getAllIngredients()
                        .onEach {dataStateAllergen ->
                            _dataStateIngredients.value = dataStateAllergen
                        }
                        .launchIn(viewModelScope)
                }

                is GetTags -> {
                    obRepo.getAllTags().onEach { dataStateTags ->
                        _dataStateTags.value = dataStateTags
                    }.launchIn(viewModelScope)
                }

                is GetCuisines -> {
                    obRepo.getAllTypesOfCuisines()
                        .onEach {dataStateCuisine ->
                            _dataStateCuisines.value = dataStateCuisine
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }


    fun searchRecipes(searchParam : SearchParam){
        viewModelScope.launch {
            obRepo.searchRecipes(searchParam)
                .onEach { dataStateRecipes ->
                    _dataStateRecipes.value = dataStateRecipes
                }.launchIn(viewModelScope)
        }
    }


    fun setIngredientsParam(
        selections: java.util.HashMap<String, Boolean>,
        exclusions: java.util.HashMap<String, Boolean>,
        forSearch: Boolean,
        name: String
    ) {
        val ingredientExclusions: MutableList<String> = mutableListOf()
        val ingredientSelections: MutableList<String> = mutableListOf()
        selections.onEach {
            if(it.value) ingredientSelections.add(it.key.lowercase())
        }
        exclusions.onEach {
            if(it.value) ingredientExclusions.add(it.key.lowercase())
        }

        if(forSearch){
            var param : SearchParam? = _searchParam.value
            if(param != null){
                param.ingredientExclusions = ingredientExclusions
                param.ingredients = ingredientSelections
            }else{
                param = SearchParam(ingredients = ingredientSelections, ingredientExclusions = ingredientExclusions)
            }
            _searchParam.value = param!!
        }else{
            var profile : Profile? = _profile.value
            if(profile != null){
                profile.param.ingredientExclusions = ingredientExclusions
                profile.param.ingredients = ingredientSelections
            }else{
                val param = SearchParam()
                param.ingredientExclusions = ingredientExclusions
                param.ingredients = ingredientSelections
                val profile = Profile(name, param)
            }
        }
    }


    fun setTagsParam(
        selections: java.util.HashMap<String, Boolean>,
        exclusions: java.util.HashMap<String, Boolean>
    ) {
        val tagExclusions: MutableList<String> = mutableListOf()
        val tagSelections: MutableList<String> = mutableListOf()
        selections.onEach {
            if(it.value) tagSelections.add(it.key.lowercase())
        }
        exclusions.onEach {
            if(it.value) tagExclusions.add(it.key.lowercase())
        }
        var param : SearchParam? = _searchParam.value
        if(param != null){
            param.tagExclusions = tagExclusions
            param.tags = tagSelections
        }else{
            param = SearchParam(tags = tagSelections, tagExclusions = tagExclusions)
        }
        _searchParam.value = param!!
    }


    sealed class QueryEvent{
        object GetIngredients: QueryEvent()
        object GetCuisines: QueryEvent()
        object GetTags: QueryEvent()
    }
}