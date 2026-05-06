package com.barron

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FoodActivity : AppCompatActivity() {

    private lateinit var meals: List<Meal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val tvResumen = findViewById<TextView>(R.id.tvResumen)
        val recycler = findViewById<RecyclerView>(R.id.rvMeals)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        recycler.layoutManager = LinearLayoutManager(this)

        val dietManager = DietManager()
        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        // 🔒 SEGURO (sin crashes)
        val peso = prefs.getString("peso", "70")?.toDoubleOrNull() ?: 70.0
        val objetivo = prefs.getString("objetivo", "Mantener") ?: "Mantener"

        val totalKcal = dietManager.calculateCalories(peso, objetivo)
        meals = dietManager.getSampleMeals()

        val consumed = meals.sumOf { it.calories }
        val remaining = dietManager.remainingCalories(totalKcal, consumed)

        // 🧠 UI
        tvResumen.text = """
            🔥 Objetivo: $totalKcal kcal
            🍴 Consumido: $consumed kcal
            ⚡ Restante: $remaining kcal
        """.trimIndent()

        recycler.adapter = MealAdapter(meals)

        // 💾 GUARDAR TODO CORRECTO
        btnGuardar.setOnClickListener {

            val db = FirebaseDatabase.getInstance().getReference("progress")
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date())

            // 🔥 1. SISTEMA VIEJO (NO SE BORRA)
            db.child("todayCalories").setValue(consumed)

            // 🔥 2. SISTEMA NUEVO (POR USUARIO + FECHA)
            if (userId != null) {
                db.child(userId)
                    .child(date)
                    .setValue(consumed)
            }

            // 🔥 3. GUARDAR META LOCAL
            prefs.edit().putString("goalCalories", totalKcal.toString()).apply()

            Toast.makeText(this, "Datos guardados 🔥", Toast.LENGTH_SHORT).show()
        }
    }
}