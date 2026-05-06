package com.barron

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.barron.databinding.ActivityProgressBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("progress")

        loadData()
    }

    private fun loadData() {

        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val goal = prefs.getString("goalCalories", "2000")
            ?.toIntOrNull() ?: 2000

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date())

        // 🔥 PRIORIDAD: nuevo sistema
        if (userId != null) {

            database.child(userId)
                .child(date)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        var consumed = snapshot.getValue(Int::class.java)
                            ?: snapshot.getValue(Double::class.java)?.toInt()

                        // 🔥 SI NO HAY DATOS → usa el sistema viejo
                        if (consumed == null) {

                            database.child("todayCalories")
                                .addListenerForSingleValueEvent(object : ValueEventListener {

                                    override fun onDataChange(snapshot2: DataSnapshot) {

                                        val fallback = snapshot2.getValue(Int::class.java)
                                            ?: snapshot2.getValue(Double::class.java)?.toInt()
                                            ?: 0

                                        updateUI(goal, fallback)
                                    }

                                    override fun onCancelled(error: DatabaseError) {}
                                })

                        } else {
                            updateUI(goal, consumed)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })

        } else {
            // 🔥 sin usuario → usa viejo sistema
            database.child("todayCalories")
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val consumed = snapshot.getValue(Int::class.java)
                            ?: snapshot.getValue(Double::class.java)?.toInt()
                            ?: 0

                        updateUI(goal, consumed)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }
    private fun updateUI(goal: Int, consumed: Int) {

        val remaining = (goal - consumed).coerceAtLeast(0)

        binding.tvCalories.text = "Consumido: $consumed kcal"
        binding.tvGoal.text = "Meta: $goal kcal"
        binding.tvRemaining.text = "Restante: $remaining kcal"

        setupPieChart(consumed, remaining)
    }
    private fun setupPieChart(consumed: Int, remaining: Int) {

        val entries = arrayListOf(
            PieEntry(consumed.toFloat(), "Consumido 🔥"),
            PieEntry(remaining.toFloat(), "Restante ⚡")
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.parseColor("#FF5722"),
            Color.parseColor("#4CAF50")
        )

        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)

        binding.pieChart.data = data

        binding.pieChart.apply {
            description.isEnabled = false
            setUsePercentValues(false)
            isDrawHoleEnabled = true
            holeRadius = 65f
            setHoleColor(Color.WHITE)
            centerText = "Calorías"
            setCenterTextSize(18f)
            animateY(1200)
            legend.isEnabled = true
            invalidate()
        }
    }
}