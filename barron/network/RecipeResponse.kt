package com.barron.network

data class RecipeResponse(
    val results: List<RecipeDto>
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val nutrition: NutritionWrapper?
)

data class NutritionWrapper(
    val nutrients: List<Nutrient>
)

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String
)