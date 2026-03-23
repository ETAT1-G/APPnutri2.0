package com.appbarron.utils

object KcalCalculator {

    fun calcular(peso: Double, meta: String): Int {
        return when (meta.lowercase()) {
            "bajar" -> (peso * 20).toInt()
            "subir" -> (peso * 30).toInt()
            else -> (peso * 25).toInt()
        }
    }
}