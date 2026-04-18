package com.barron

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        if (prefs.contains("nombre")) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)

        val nombre = findViewById<EditText>(R.id.etNombre)
        val edad = findViewById<EditText>(R.id.etEdad)
        val peso = findViewById<EditText>(R.id.etPeso)
        val enfermedad = findViewById<EditText>(R.id.etEnfermedad)
        val presupuesto = findViewById<EditText>(R.id.etPresupuesto)
        val objetivo = findViewById<Spinner>(R.id.spObjetivo)
        val boton = findViewById<Button>(R.id.btnGuardar)

        val opciones = arrayOf("Bajar peso", "Subir masa", "Mantener")
        objetivo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)

        boton.setOnClickListener {
            val editor = prefs.edit()
            editor.putString("nombre", nombre.text.toString())
            editor.putString("edad", edad.text.toString())
            editor.putString("peso", peso.text.toString())
            editor.putString("enfermedad", enfermedad.text.toString())
            editor.putString("presupuesto", presupuesto.text.toString())
            editor.putString("objetivo", objetivo.selectedItem.toString())
            editor.apply()

            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}