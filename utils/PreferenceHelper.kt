package com.barron.utils

import android.content.Context

class PreferenceHelper(context: Context) {

    private val prefs = context.getSharedPreferences("food_prefs", Context.MODE_PRIVATE)

    fun saveTime(key: String, hour: Int, minute: Int) {
        prefs.edit()
            .putInt("${key}_hour", hour)
            .putInt("${key}_minute", minute)
            .apply()
    }

    fun getHour(key: String): Int {
        return prefs.getInt("${key}_hour", -1)
    }

    fun getMinute(key: String): Int {
        return prefs.getInt("${key}_minute", -1)
    }
}