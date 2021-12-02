package com.smartchef.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartchef.model.Recipe
import com.smartchef.model.SearchParam
import com.smartchef.repository.OnboardingRepository
import com.smartchef.util.DataState
import com.smartchef.ui.onboarding.AppViewModel.QueryEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class AppViewModel @Inject constructor(private val obRepo : OnboardingRepository): ViewModel() {

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

                is GetIngredientSelections ->{
                    obRepo.getSelectedIngredients().onEach {  }
                }

                is GetTags -> {
                    obRepo.getAllTags().onEach { dataStateTags ->
                        _dataStateTags.value = dataStateTags
                    }.launchIn(viewModelScope)
                }

                is GetSelectedTags -> {

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
        exclusions: java.util.HashMap<String, Boolean>
    ) {
        val ingredientExclusions: MutableList<String> = mutableListOf()
        val ingredientSelections: MutableList<String> = mutableListOf()
        selections.onEach {
            if(it.value) ingredientSelections.add(it.key.lowercase())
        }
        exclusions.onEach {
            if(it.value) ingredientExclusions.add(it.key.lowercase())
        }
        var param : SearchParam? = _searchParam.value
        if(param != null){
            param.ingredientExclusions = ingredientExclusions
            param.ingredients = ingredientSelections
        }else{
            param = SearchParam(ingredients = ingredientSelections, ingredientExclusions = ingredientExclusions)
        }
        _searchParam.value = param!!
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
        object GetIngredientSelections: QueryEvent()
        object GetCuisines: QueryEvent()
        object GetCuisinesSelections: QueryEvent()
        object GetTags: QueryEvent()
        object GetSelectedTags: QueryEvent()
    }
}