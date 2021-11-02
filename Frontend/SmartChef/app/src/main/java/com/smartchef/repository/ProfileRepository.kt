package com.smartchef.repository

import com.smartchef.network.WebAPI

class ProfileRepository {

    private val webservice: WebAPI = TODO()
    // ...
    suspend fun getUser(userId: String) =
    // This isn't an optimal implementation because it doesn't take into
    // account caching. We'll look at how to improve upon this in the next
        // sections.
        webservice.getUser(userId)

}