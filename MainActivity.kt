package com.tu.paquete // Asegúrate de tener tu package name real aquí

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val miGato = findViewById<ImageView>(R.id.ivMascota)
        val btnTerminar = findViewById<Button>(R.id.btnTerminar)

        // 1. Iniciamos la animación normal (la lenta)
        miGato.setImageResource(R.drawable.animacion_gato)
        val animacionLenta = miGato.drawable as AnimationDrawable
        animacionLenta.start()

        // 2. Configuramos qué pasa al presionar el botón
        btnTerminar.setOnClickListener {
            
            // Detenemos la animación actual
            animacionLenta.stop()

            // Cambiamos a la animación rápida
            miGato.setImageResource(R.drawable.animacion_gato_rapida)
            val animacionRapida = miGato.drawable as AnimationDrawable
            animacionRapida.start()

            // Mostramos un mensaje de felicitación
            Toast.makeText(this, "¡Excelente trabajo! ¡El gato está orgulloso!", Toast.LENGTH_LONG).show()
        }
    }
}