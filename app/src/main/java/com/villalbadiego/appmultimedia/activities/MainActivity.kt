package com.villalbadiego.appmultimedia.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.villalbadiego.appmultimedia.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inflamos los botones.
        val botonDibujo = findViewById<Button>(R.id.btnDrawing)
        val botonSonido = findViewById<Button>(R.id.btnSound)
        val botonVideo = findViewById<Button>(R.id.btnVideo)

        // Navegamos a la actividad de dibujo.
        botonDibujo.setOnClickListener {
            val intent = Intent(this, DrawingActivity::class.java)
            startActivity(intent)
        }
        // Navegamos a la actividad de sonido.
        botonSonido.setOnClickListener {
            val intent = Intent(this, SoundActivity::class.java)
            startActivity(intent)
        }
        // Navegamos a la actividad de video.
        botonVideo.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}