package com.barron.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val type = intent?.getIntExtra("type", 0) ?: 0

        val helper = NotificationHelper(context)

        when (type) {
            1 -> helper.showNotification("Desayuno 🍳", "Hora de iniciar tu día con energía")
            2 -> helper.showNotification("Comida 🍛", "Recuerda tu comida principal")
            3 -> helper.showNotification("Cena 🌙", "Cena ligero para descansar mejor")
            else -> helper.showNotification("Recordatorio", "No olvides comer")
        }
    }
}