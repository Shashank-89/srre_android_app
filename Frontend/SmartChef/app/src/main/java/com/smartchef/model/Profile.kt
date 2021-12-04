package com.smartchef.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Profile(
    val name: String,
    val param:SearchParam
)
