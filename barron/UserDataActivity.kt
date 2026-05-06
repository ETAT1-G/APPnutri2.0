package com.barron

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        val nombre = findViewById<EditText>(R.id.etNombre)
        val edad = findViewById<EditText>(R.id.etEdad)
        val peso = findViewById<EditText>(R.id.etPeso)
        val enfermedad = findViewById<EditText>(R.id.etEnfermedad)
        val presupuesto = findViewById<EditText>(R.id.etPresupuesto)
        val objetivo = findViewById<Spinner>(R.id.spObjetivo)
        val boton = findViewById<Button>(R.id.btnGuardar)

        val opciones = arrayOf(
            "Bajar peso",
            "Subir masa",
            "Mantener",
            "Mejorar resistencia",
            "Ganar fuerza",
            "Definición muscular",
            "Salud general"
        )

        objetivo.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            opciones
        )
        objetivo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)

        boton.setOnClickListener {

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val database = FirebaseDatabase.getInstance().reference

            val userMap = HashMap<String, Any>()
            userMap["nombre"] = nombre.text.toString()
            userMap["edad"] = edad.text.toString()
            userMap["peso"] = peso.text.toString()
            userMap["enfermedad"] = enfermedad.text.toString()
            userMap["presupuesto"] = presupuesto.text.toString()
            userMap["objetivo"] = objetivo.selectedItem.toString()

            database.child("usuarios").child(userId)
                .setValue(userMap)

            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}