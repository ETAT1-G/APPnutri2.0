package com.barron

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.barron.utils.AlarmHelper
import com.barron.utils.PreferenceHelper
import java.util.*

class MenuActivity : AppCompatActivity() {

    private lateinit var prefs: PreferenceHelper
    private lateinit var alarmHelper: AlarmHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        prefs = PreferenceHelper(this)
        alarmHelper = AlarmHelper(this)

        // BOTONES PRINCIPALES
        findViewById<Button>(R.id.btnComidas).setOnClickListener {
            startActivity(Intent(this, FoodActivity::class.java))
        }

        findViewById<Button>(R.id.btnMapa).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        findViewById<Button>(R.id.btnProgreso).setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        // VISTAS
        val btnBreakfast = findViewById<Button>(R.id.btnHoraBreakfast)
        val btnLunch = findViewById<Button>(R.id.btnHoraLunch)
        val btnDinner = findViewById<Button>(R.id.btnHoraDinner)

        val switchBreakfast = findViewById<Switch>(R.id.switchBreakfast)
        val switchLunch = findViewById<Switch>(R.id.switchLunch)
        val switchDinner = findViewById<Switch>(R.id.switchDinner)

        // VALORES INICIALES
        setInitialTime(btnBreakfast, "breakfast", 8, 0)
        setInitialTime(btnLunch, "lunch", 14, 0)
        setInitialTime(btnDinner, "dinner", 20, 0)

        // PICKERS DE HORA
        btnBreakfast.setOnClickListener { pickTime(btnBreakfast, "breakfast", 1) }
        btnLunch.setOnClickListener { pickTime(btnLunch, "lunch", 2) }
        btnDinner.setOnClickListener { pickTime(btnDinner, "dinner", 3) }

        // SWITCHES
        switchBreakfast.setOnCheckedChangeListener { _, isChecked ->
            toggleAlarm(isChecked, "breakfast", 1)
        }

        switchLunch.setOnCheckedChangeListener { _, isChecked ->
            toggleAlarm(isChecked, "lunch", 2)
        }

        switchDinner.setOnCheckedChangeListener { _, isChecked ->
            toggleAlarm(isChecked, "dinner", 3)
        }
    }

    private fun setInitialTime(button: Button, key: String, defaultHour: Int, defaultMinute: Int) {
        val hour = prefs.getHour(key, defaultHour)
        val minute = prefs.getMinute(key, defaultMinute)

        button.text = String.format("%02d:%02d", hour, minute)
    }

    private fun pickTime(button: Button, key: String, requestCode: Int) {
        val calendar = Calendar.getInstance()

        TimePickerDialog(this, { _, hour, minute ->
            button.text = String.format("%02d:%02d", hour, minute)

            prefs.saveTime(key, hour, minute)
            alarmHelper.scheduleDailyNotification(hour, minute, requestCode)

            Toast.makeText(this, "Hora guardada", Toast.LENGTH_SHORT).show()

        },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
    private fun toggleAlarm(enabled: Boolean, key: String, requestCode: Int) {
        val hour = prefs.getHour(key)
        val minute = prefs.getMinute(key)

        prefs.setEnabled(key, enabled)

        if (enabled) {
            alarmHelper.scheduleDailyNotification(hour, minute, requestCode)
            Toast.makeText(this, "Alarma activada", Toast.LENGTH_SHORT).show()
        } else {
            alarmHelper.cancelNotification(requestCode)
            Toast.makeText(this, "Alarma desactivada", Toast.LENGTH_SHORT).show()
        }
    }
}