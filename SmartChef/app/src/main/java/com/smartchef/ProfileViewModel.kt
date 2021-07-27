package com.smartchef

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ProfileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    //name, email, dietary profile
    val userId : String = savedStateHandle["uid"] ?: throw IllegalArgumentException("missing user id")
    val user : LiveData<User> = TODO()

}