package com.barron

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val boton = findViewById<Button>(R.id.btnRegister)

        boton.setOnClickListener {

            val emailTexto = email.text.toString()
            val passwordTexto = password.text.toString()

            if (emailTexto.isEmpty() || passwordTexto.isEmpty()) {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔥 INTENTA REGISTRAR
            auth.createUserWithEmailAndPassword(emailTexto, passwordTexto)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, UserDataActivity::class.java))
                        finish()

                    } else {

                        val error = task.exception?.message

                        if (error?.contains("already in use") == true) {

                            // 🔥 SI YA EXISTE → INTENTA LOGIN
                            auth.signInWithEmailAndPassword(emailTexto, passwordTexto)
                                .addOnCompleteListener { loginTask ->
                                    if (loginTask.isSuccessful) {

                                        Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MenuActivity::class.java))
                                        finish()

                                    } else {
                                        Toast.makeText(this, "El correo ya existe, pero la contraseña es incorrecta", Toast.LENGTH_LONG).show()
                                    }
                                }

                        } else {
                            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}