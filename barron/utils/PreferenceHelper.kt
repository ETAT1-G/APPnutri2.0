package com.barron.utils

import android.content.Context

class PreferenceHelper(context: Context) {

    private val prefs = context.getSharedPreferences("food_prefs", Context.MODE_PRIVATE)
    fun getHour(key: String, defaultHour: Int = 8): Int {
        return prefs.getInt("${key}_hour", defaultHour)
    }

    fun getMinute(key: String, defaultMinute: Int = 0): Int {
        return prefs.getInt("${key}_minute", defaultMinute)
    }

    fun setEnabled(key: String, enabled: Boolean) {
        prefs.edit().putBoolean("${key}_enabled", enabled).apply()
    }

    fun isEnabled(key: String): Boolean {
        return prefs.getBoolean("${key}_enabled", false)
    }
    fun saveTime(key: String, hour: Int, minute: Int) {
        prefs.edit()
            .putInt("${key}_hour", hour)
            .putInt("${key}_minute", minute)
            .apply()
    }
}