package com.tuapp.gato

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    val gatoVM: GatoViewModel by viewModels()
    private val system = GatoSystem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        system.attach(this, gatoVM)
    }

    override fun onDestroy() {
        super.onDestroy()
        system.destroy()
    }
}