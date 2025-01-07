package com.villalbadiego.appmultimedia.activities

import android.health.connect.datatypes.units.Volume
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.villalbadiego.appmultimedia.R

class SoundActivity : AppCompatActivity() {
    private var mediaPlayer1 : MediaPlayer? = null
    private var mediaPlayer2 : MediaPlayer? = null

    private lateinit var seekBarVolume: SeekBar
    private lateinit var seekBarProgress: SeekBar
    private var estaReproduciendo1 = false
    private var estaReproduciendo2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        // Inicialización de los Seekbar.
        seekBarVolume = findViewById(R.id.seekBarVolume)
        seekBarProgress = findViewById(R.id.seekBarProgress)

        // Inicialización de los botones.
        val btnSound1 : Button = findViewById(R.id.btnSound1)
        val btnSound2 : Button = findViewById(R.id.btnSound2)

        // Crear instancias de los MediaPlayer.
        mediaPlayer1 = MediaPlayer.create(this, R.raw.sound1)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.sound2)
    }
}