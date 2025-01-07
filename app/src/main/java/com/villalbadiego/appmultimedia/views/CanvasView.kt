package com.villalbadiego.appmultimedia.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.villalbadiego.appmultimedia.R

class CanvasView(context : Context, atributos : AttributeSet?) : View(context, atributos){
    private val pintar = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private var anguloRotacion = 0f

    init {
        // Código para la animación de logo.
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                anguloRotacion = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centroX = width / 2f
        val centroY = height / 2f

        canvas.save()
        canvas.rotate(anguloRotacion, centroX, centroY)

        // Dibujamos un círculo.
        pintar.color = resources.getColor(R.color.fondo_circulo, null)
        canvas.drawCircle(centroX, centroY - 200f, 100f, pintar)

        // Dibujamos un rectángulo.
        pintar.color = resources.getColor(R.color.fondo_rectangulo, null)
        canvas.drawRect(centroX - 150, centroY - 50, centroX + 150, centroY + 50, pintar)

        // Dibujamos una elipse.
        pintar.color = resources.getColor(R.color.fondo_elipse, null)
        canvas.drawOval(
            RectF(centroX - 200, centroY + 150, centroX + 200, centroY + 250),
            pintar
        )

        canvas.restore()
    }
}