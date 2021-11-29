/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smartchef.ui.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.smartchef.BuildConfig
import com.smartchef.model.Profile

class AuthViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    var token:String? = null

    var profile: Profile? = null

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
            user.getIdToken(false).addOnCompleteListener{
                if(it.isSuccessful){
                    token = it.result?.token
                    if(BuildConfig.DEBUG) Log.d("AuthVM", "token result : $token")
                }else{
                    token = null
                    AuthenticationState.INVALID_AUTHENTICATION
                    if(BuildConfig.DEBUG) Log.e("AuthVM", "Not able to get Token : ${it.exception?.message}")
                }
            }

        } else {
            token = null
            AuthenticationState.UNAUTHENTICATED
        }
    }

}
