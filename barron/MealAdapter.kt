package com.barron

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barron.databinding.ItemMealBinding

class MealAdapter(private val meals: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]

        holder.binding.tvName.text = meal.name
        holder.binding.tvCalories.text = "${meal.calories} kcal"
    }

    override fun getItemCount() = meals.size
}