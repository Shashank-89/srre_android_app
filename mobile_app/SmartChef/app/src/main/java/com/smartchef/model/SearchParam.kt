package com.smartchef.model

import com.google.gson.annotations.SerializedName

data class SearchParam(
    @SerializedName("ingredients") var ingredients: List<String> = listOf(),
    @SerializedName("ingredient_exclusions") var ingredientExclusions: List<String> = listOf(),
    @SerializedName("tags") var tags: List<String> = listOf(),
    @SerializedName("tag_exclusions") var tagExclusions: List<String> = listOf(),
    @SerializedName("limit") val limit: Int = 10)