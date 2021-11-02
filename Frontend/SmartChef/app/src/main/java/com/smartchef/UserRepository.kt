package com.smartchef

import com.smartchef.room.User
import javax.inject.Inject

class UserRepository @Inject constructor ()
{
        suspend fun getUser(userId: String): User {
            //TODO implement memory cache, retrieve from local Room db, use webservice
            return User("42", "john.doe@gmail.com", "john", "doe", null)
        }

}