package com.barron // Cambia esto por el paquete de tu proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Cambia "tu_layout" por el nombre real del XML

        // Vincular vistas
        nombre = findViewById(R.id.nombre)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnRegister)

        // Evento del botón
        btnRegister.setOnClickListener {

            val nombreTexto = nombre.text.toString().trim()
            val emailTexto = email.text.toString().trim()
            val passwordTexto = password.text.toString().trim()

            // Validaciones básicas
            if (nombreTexto.isEmpty() || emailTexto.isEmpty() || passwordTexto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí puedes conectar con Firebase después
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}