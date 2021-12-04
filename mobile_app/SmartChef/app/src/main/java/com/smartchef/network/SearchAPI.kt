package com.smartchef.network

import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import com.smartchef.model.SearchParam
import com.smartchef.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SearchAPI {

    @POST("search")
    suspend fun searchRecipes(@Body searchParam : SearchParam): JsonObject

}