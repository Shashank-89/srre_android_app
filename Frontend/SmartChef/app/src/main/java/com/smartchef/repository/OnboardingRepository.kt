package com.smartchef.repository

import com.smartchef.network.WebAPI
import com.smartchef.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnboardingRepository @Inject constructor(){

    //get cuisines (tags), allergens (ingredients)
    fun getAllergens(): Flow<DataState<List<String>>> = flow {
        emit(DataState.Success(listOf(
            "peanuts",
            "tree nuts",
            "milk",
            "eggs",
            "fish",
            "shellfish",
            "soy",
            "wheat",
            "sesame seeds",
            "mustard",
            "gluten",
            "celery",
            "lupin"
        )))
    }

    fun getTypesOfCuisines(): Flow<DataState<List<String>>> = flow {
        emit(DataState.Success(listOf(
            "Mexican",
            "Swedish",
            "Latvian",
            "Italian",
            "Spanish",
            "American",
            "Scottish",
            "British",
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
            "Tibetan",
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
