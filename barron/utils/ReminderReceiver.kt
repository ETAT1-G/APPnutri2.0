package com.barron.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.barron.utils.AlarmHelper

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val channelId = "meal_channel"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal (OBLIGATORIO Android 8+)
        val channel = NotificationChannel(
            channelId,
            "Recordatorios de comida",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Hora de comer 🍽️")
            .setContentText("No olvides tu comida saludable")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val id = intent.getIntExtra("requestCode", 0)

        notificationManager.notify(id, notification)

        // 🔁 REPROGRAMAR (clave estilo iPhone)
        val prefs = PreferenceHelper(context)
        val alarmHelper = AlarmHelper(context)

        val key = when (id) {
            1 -> "breakfast"
            2 -> "lunch"
            else -> "dinner"
        }

        val hour = prefs.getHour(key)
        val minute = prefs.getMinute(key)

        alarmHelper.scheduleDailyNotification(hour, minute, id)
    }
}