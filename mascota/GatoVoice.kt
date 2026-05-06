package com.tuapp.gato

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class GatoVoice(context: Context) {

    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("es", "ES")
                tts?.setSpeechRate(0.95f)
            }
        }
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun release() {
        tts?.shutdown()
        tts = null
    }
}