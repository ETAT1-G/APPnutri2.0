package com.tuapp.gato

data class GatoUIModel(
    val imageRes: Int,
    val message: String,
    val speak: Boolean = false
)