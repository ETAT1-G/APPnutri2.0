package com.tuapp.gato

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GatoViewModel : ViewModel() {

    private val _state = MutableStateFlow<GatoUIModel?>(null)
    val state: StateFlow<GatoUIModel?> = _state

    fun update(model: GatoUIModel) {
        _state.value = model
    }
}