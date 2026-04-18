package com.barron

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.barron.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Datos simulados (puedes conectar Firebase después)
        val caloriasConsumidas = 1400
        val meta = 2000

        val progreso = (caloriasConsumidas * 100) / meta

        // UI dinámica
        binding.tvProgress.text = "Progreso: $progreso%"
        binding.progressBar.progress = progreso

        binding.tvCalories.text = "Calorías consumidas: $caloriasConsumidas kcal"
        binding.tvGoal.text = "Meta diaria: $meta kcal"
    }
}