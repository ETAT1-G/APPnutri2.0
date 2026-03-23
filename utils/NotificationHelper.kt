package com.appbarron.utils

import android.app.*
import android.content.Context
import androidx.core.app.NotificationCompat

class NotificationHelper(private val context: Context) {

    fun mostrarRecordatorio(kcalRestantes: Int) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "food",
            "Recordatorio comida",
            NotificationManager.IMPORTANCE_HIGH
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "food")
            .setContentTitle("🍎 Sigue así!")
            .setContentText("Te quedan $kcalRestantes kcal hoy")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        manager.notify(1, notification)
    }
}