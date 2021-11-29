package com.smartchef.model

import com.google.gson.annotations.SerializedName

data class Profile(
    val id: String,
    val name: String,
    val param:SearchParam
)
