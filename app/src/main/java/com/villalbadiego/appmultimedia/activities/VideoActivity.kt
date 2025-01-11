package com.villalbadiego.appmultimedia.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.villalbadiego.appmultimedia.R

class VideoActivity : AppCompatActivity() {
    private lateinit var styledPlayerView: StyledPlayerView
    private lateinit var progressBar: ProgressBar
    private var exoPlayer: ExoPlayer? = null
    private var estaReproduciendo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        // Inicialización de las vistas
        styledPlayerView = findViewById(R.id.playerView)
        progressBar = findViewById(R.id.progressBar)

        // Configuración de ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            styledPlayerView.player = this // Usamos styledPlayerView para controlar el reproductor
        }

        // Cargar y preparar el video
        val videoUri = Uri.parse("android.resource://${packageName}/raw/video_ejemplo")
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer?.setMediaItem(mediaItem)

        exoPlayer?.prepare()

        // Configurar el seguimiento del progreso del video
        exoPlayer?.addListener(object : com.google.android.exoplayer2.Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    com.google.android.exoplayer2.Player.STATE_READY -> {
                        progressBar.visibility = View.GONE // Ocultar progress bar cuando esté listo
                        estaReproduciendo = true
                    }
                    com.google.android.exoplayer2.Player.STATE_ENDED -> {
                        estaReproduciendo = false
                    }
                    com.google.android.exoplayer2.Player.STATE_IDLE -> {
                        progressBar.visibility = View.VISIBLE // Mostrar progress bar cuando esté cargando
                    }
                    else -> {}
                }
            }
        })

        // Iniciar la reproducción del video
        reproducirVideo()
    }

    private fun reproducirVideo() {
        exoPlayer?.play()
        estaReproduciendo = true
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
    }
}
