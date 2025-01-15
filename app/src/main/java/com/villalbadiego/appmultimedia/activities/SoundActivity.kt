package com.villalbadiego.appmultimedia.activities

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.villalbadiego.appmultimedia.R

class SoundActivity : AppCompatActivity() {
    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null

    private lateinit var seekBarVolume: SeekBar
    private lateinit var seekBarProgress1: SeekBar
    private lateinit var seekBarProgress2: SeekBar
    private var estaReproduciendo1 = false
    private var estaReproduciendo2 = false

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        // Inicialización de los Seekbar
        seekBarVolume = findViewById(R.id.seekBarVolume)
        seekBarProgress1 = findViewById(R.id.seekBarProgress1)
        seekBarProgress2 = findViewById(R.id.seekBarProgress2)

        // Inicialización de los botones
        val btnSound1: Button = findViewById(R.id.btnSound1)
        val btnSound2: Button = findViewById(R.id.btnSound2)

        // Crear instancias de los MediaPlayer
        mediaPlayer1 = MediaPlayer.create(this, R.raw.sound1)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.sound2)

        // Configuración del volumen con SeekBar
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        seekBarVolume.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBarVolume.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progreso: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progreso, 0)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    
        // Configuración de los botones
        btnSound1.setOnClickListener {
            if (estaReproduciendo1) {
                mediaPlayer1?.pause()
                estaReproduciendo1 = false
            } else {
                mediaPlayer1?.start()
                estaReproduciendo1 = true
                actualizarProgreso(mediaPlayer1, seekBarProgress1)
            }
        }

        btnSound2.setOnClickListener {
            if (estaReproduciendo2) {
                mediaPlayer2?.pause()
                estaReproduciendo2 = false
            } else {
                mediaPlayer2?.start()
                estaReproduciendo2 = true
                actualizarProgreso(mediaPlayer2, seekBarProgress2)
            }
        }

        // Configuración de las barras de progreso
        mediaPlayer1?.let { seekBarProgress1.max = it.duration }
        mediaPlayer2?.let { seekBarProgress2.max = it.duration }

        seekBarProgress1.setOnSeekBarChangeListener(createSeekBarChangeListener(mediaPlayer1))
        seekBarProgress2.setOnSeekBarChangeListener(createSeekBarChangeListener(mediaPlayer2))

        // Listeners para completar el audio
        mediaPlayer1?.setOnCompletionListener {
            seekBarProgress1.progress = 0
            estaReproduciendo1 = false
        }

        mediaPlayer2?.setOnCompletionListener {
            seekBarProgress2.progress = 0
            estaReproduciendo2 = false
        }
    }

    private fun actualizarProgreso(mediaPlayer: MediaPlayer?, seekBar: SeekBar) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying) {
                    seekBar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    private fun createSeekBarChangeListener(mediaPlayer: MediaPlayer?): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer1?.pause()
        mediaPlayer2?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer1?.release()
        mediaPlayer2?.release()
        handler.removeCallbacksAndMessages(null)
    }
}
