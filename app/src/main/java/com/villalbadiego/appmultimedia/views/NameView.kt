package com.villalbadiego.appmultimedia.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.villalbadiego.appmultimedia.R

class NameView(contexto: Context, atributos: AttributeSet?) : View(contexto, atributos) {
    private val pintar = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = resources.getColor(R.color.color_nombre, null)
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Coordenadas del centro
        val centroX = width / 2f
        val centroY = height / 2f

        // Dibujar un círculo para el fondo del nombre
        pintar.color = resources.getColor(R.color.fondo_circulo, null)
        canvas.drawCircle(centroX, centroY, 150f, pintar)

        // Dibujar el nombre inclinado
        pintar.color = resources.getColor(R.color.color_nombre, null)
        canvas.save()
        canvas.rotate(-45f, centroX, centroY) // Rotar el texto -45 grados
        canvas.drawText(resources.getString(R.string.nombre_autor), centroX, centroY, pintar)
        canvas.restore()
    }
}