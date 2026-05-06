package com.tuapp.gato

import kotlin.random.Random

object GatoBrain {

    private fun pick(list: List<String>) =
        list[Random.nextInt(list.size)]

    fun login() = GatoUIModel(
        imageRes = R.drawable.gatomamado,
        message = pick(listOf(
            "Activa disciplina 🔥",
            "¿Listo para entrenar?",
            "Sin excusas hoy."
        )),
        speak = true
    )

    fun error() = GatoUIModel(
        imageRes = R.drawable.gatomamado,
        message = pick(listOf(
            "Error detectado... mejora técnica.",
            "Eso no fue óptimo.",
            "Concéntrate."
        )),
        speak = true
    )

    fun loading() = GatoUIModel(
        imageRes = R.drawable.gatomamado,
        message = "Procesando...",
        speak = false
    )

    fun normal() = GatoUIModel(
        imageRes = R.drawable.gatomamado,
        message = "Todo bajo control 💪",
        speak = false
    )
}