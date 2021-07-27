package com.smartchef

import retrofit2.http.GET
import retrofit2.http.Path

interface WebAPI {

    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String): User


}