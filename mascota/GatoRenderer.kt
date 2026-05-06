package com.tuapp.gato

import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView

class GatoRenderer(
    private val image: ImageView,
    private val text: TextView
) {

    private val pop = OvershootInterpolator(2f)

    fun render(model: GatoUIModel) {

        image.animate().cancel()

        image.animate()
            .alpha(0f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(120)
            .withEndAction {

                image.setImageResource(model.imageRes)
                text.text = model.message

                image.scaleX = 0.8f
                image.scaleY = 0.8f

                image.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(pop)
                    .setDuration(300)
                    .start()
            }
            .start()
    }
}