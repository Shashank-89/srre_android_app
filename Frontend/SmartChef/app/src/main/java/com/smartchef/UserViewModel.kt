package com.smartchef

import android.util.Log
import androidx.lifecycle.*
import com.smartchef.room.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(savedState : SavedStateHandle, userRepository: UserRepository) : ViewModel() {

    val userId : String = savedState["uid"] ?: "42"//throw IllegalArgumentException("missing user id")
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user
    init{
        Log.i("ProfileViewModel", "ProfileViewModel created!")
        viewModelScope.launch { _user.value = userRepository.getUser(userId) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ProfileViewModel", "ProfileViewModel destroyed!")
        //TODO handle clearing logic here
    }

}
