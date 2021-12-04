package com.smartchef.model

import com.google.gson.annotations.SerializedName

data class Recipe (
    @SerializedName("rid")  val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("servings") val servings: String,
    @SerializedName("steps") val rawSteps: String,
    @SerializedName("image") val imgUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("minutes") val prepTime: Double,

    @SerializedName("cal") val cal: Double,
    @SerializedName("carbs") val carbs: Double,
    @SerializedName("protein") val protein: Double,
    @SerializedName("satFat") val satFat: Double,
    @SerializedName("sugar") val sugar: Double,
    @SerializedName("totalFat") val totalFat: Double,
    @SerializedName("sodium") val sodium: Double
)
