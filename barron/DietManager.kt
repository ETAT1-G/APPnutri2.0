package com.barron

data class Meal(
    val name: String,
    val calories: Int
)

class DietManager {

    fun calculateCalories(peso: Double, objetivo: String): Int {
        val base = (peso * 24).toInt()

        return when (objetivo) {
            "Bajar peso" -> base - 300
            "Subir masa" -> base + 300
            else -> base
        }
    }

    fun getSampleMeals(): List<Meal> {
        // 🔥 ESTE ES EL HUECO PARA API
        // Aquí después conectarás Spoonacular

        return listOf(
            Meal("Avena con fruta", 250),
            Meal("Pollo con arroz", 500),
            Meal("Ensalada verde", 200)
        )
    }

    fun remainingCalories(total: Int, consumed: Int): Int {
        return total - consumed
    }
}