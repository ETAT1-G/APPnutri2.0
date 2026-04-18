package com.barron

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class FoodActivity : AppCompatActivity() {

    private lateinit var meals: List<Meal> // 🔥 lista global para poder usarla en guardar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val tvComidas = findViewById<TextView>(R.id.tvComidas)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val dietManager = DietManager()

        // 🔥 DATOS DEL USUARIO
        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val peso = prefs.getString("peso", "70")!!.toDouble()
        val objetivo = prefs.getString("objetivo", "Mantener")!!

        val totalKcal = dietManager.calculateCalories(peso, objetivo)
        meals = dietManager.getSampleMeals()

        var consumed = 0
        val builder = StringBuilder()

        builder.append("🍽️ PLAN DEL DÍA\n\n")

        for (meal in meals) {
            builder.append("${meal.name} - ${meal.calories} kcal\n")
            consumed += meal.calories
        }

        val remaining = dietManager.remainingCalories(totalKcal, consumed)

        builder.append("\n🔥 Calorías objetivo: $totalKcal kcal")
        builder.append("\n🍴 Consumido: $consumed kcal")
        builder.append("\n⚡ Restante: $remaining kcal")

        tvComidas.text = builder.toString()

        // 🔥 BOTÓN GUARDAR EN FIREBASE
        btnGuardar.setOnClickListener {
            for (meal in meals) {
                saveMeal(meal)
            }
        }
    }

    // ✅ AHORA SÍ está fuera de onCreate
    private fun saveMeal(meal: Meal) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("meals")

        val id = ref.push().key!!

        ref.child(id).setValue(meal)
    }
}