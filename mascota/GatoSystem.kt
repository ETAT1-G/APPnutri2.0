package com.tuapp.gato

import android.app.Activity
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GatoSystem {

    private var voice: GatoVoice? = null

    fun attach(activity: Activity, vm: GatoViewModel) {

        val root = activity.findViewById<android.view.ViewGroup>(android.R.id.content)

        val view = LayoutInflater.from(activity)
            .inflate(R.layout.overlay_gato_macho, root, false)

        root.addView(view)

        val renderer = GatoRenderer(
            view.findViewById(R.id.img_gato_macho),
            view.findViewById(R.id.txt_frase_gato)
        )

        voice = GatoVoice(activity)

        activity.lifecycleScope.launch {
            vm.state.collectLatest { model ->
                model ?: return@collectLatest

                renderer.render(model)

                if (model.speak) {
                    voice?.speak(model.message)
                }
            }
        }
    }

    fun destroy() {
        voice?.release()
    }
}