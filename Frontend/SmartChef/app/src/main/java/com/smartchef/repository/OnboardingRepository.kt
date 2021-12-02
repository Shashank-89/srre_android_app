package com.smartchef.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.smartchef.model.Recipe
import com.smartchef.model.SearchParam
import com.smartchef.network.SearchAPI
import com.smartchef.util.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val searchAPI: SearchAPI
    ){

    //get cuisines (tags), allergens (ingredients)
    fun getAllIngredients(): Flow<DataState<List<String>>> = flow {

        val ingredientList = appContext.assets.open("ingredients.csv").bufferedReader().readLines()
        Log.e("getAllIngredients","after list populated!! size : " + ingredientList.size)
        emit(DataState.Success(
            ingredientList
        ))
    }

    fun getSelectedIngredients(): Flow<DataState<List<String>>> = flow {
        emit(DataState.Success(listOf<String>("")))
    }.transform {  }

    fun getAllTags(): Flow<DataState<List<String>>> = flow {
        val rawTagList = appContext.assets.open("tags.csv").bufferedReader().readLines()
        val tagList = mutableListOf<String>()
        rawTagList.onEach {
            if(it.isNotEmpty()) tagList.add(it)
        }
        Log.e("tagsBug", "taglist.size : " + tagList.size);
        emit(DataState.Success(tagList))
    }

    fun searchRecipes(searchParam: SearchParam): Flow<DataState<List<Recipe>>> = flow {

        emit(DataState.Loading)
        try{
            val jo: JsonObject = searchAPI.searchRecipes(searchParam)
            val gson = Gson()
            val neighbours: JsonArray = jo.getAsJsonArray("neighbors")
            val recipes: MutableList<Recipe> = mutableListOf()
            neighbours.onEach {
                recipes.add(gson.fromJson(it.asJsonArray[0], Recipe::class.java))
            }
            emit(DataState.Success(recipes))
        }catch (e : Exception){
            emit(DataState.Error(e))
        }

    }

    fun getAllTypesOfCuisines(): Flow<DataState<List<String>>> = flow {

        emit(DataState.Success(listOf<String>(
            "Mexican",
            "Swedish",
            "Italian",
            "Spanish",
            "American",
            "Scottish",
            "low-sodium",
            "low-carb",
            "lactose",
            "low-fat",
            "low-cholesterol",
            "british-columbian",
            "Thai",
            "Japanese",
            "Chinese",
            "Indian",
            "Canadian",
            "Russian",
            "Jewish",
            "Polish",
            "German",
            "French",
            "Hawaiian",
            "Brazilian",
            "Irish",
            "Greek",
            "Egyptian",
            "Cajun",
            "Portuguese"
        )))
    }
    //Ref:-https://www.listchallenges.com/world-cuisines
}

//class UserRepository @Inject constructor(
//    private val webservice: Webservice,
//    // Simple in-memory cache. Details omitted for brevity.
//    private val executor: Executor,
//    private val userDao: UserDao
//) {
//    fun getUser(userId: String): Flow<User> {
//        refreshUser(userId)
//        // Returns a Flow object directly from the database.
//        return userDao.load(userId)
//    }
//
//    private suspend fun refreshUser(userId: String) {
//        // Check if user data was fetched recently.
//        val userExists = userDao.hasUser(FRESH_TIMEOUT)
//        if (!userExists) {
//            // Refreshes the data.
//            val response = webservice.getUser(userId)
//
//            // Check for errors here.
//
//            // Updates the database. Since `userDao.load()` returns an object of
//            // `Flow<User>`, a new `User` object is emitted every time there's a
//            // change in the `User`  table.
//            userDao.save(response.body()!!)
//        }
//    }
//
//    companion object {
//        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//    }
//}