package com.villalbadiego.appmultimedia.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.villalbadiego.appmultimedia.R

class VideoActivity : AppCompatActivity() {

    private lateinit var btnRecordVideo: Button
    private lateinit var btnPlayVideo: Button
    private lateinit var playerView: StyledPlayerView
    private var exoPlayer: ExoPlayer? = null
    private var videoUri: Uri? = null

    private lateinit var recordVideoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        btnRecordVideo = findViewById(R.id.btnRecordVideo)
        btnPlayVideo = findViewById(R.id.btnPlayVideo)
        playerView = findViewById(R.id.playerView)

        checkPermissions()

        recordVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                videoUri = result.data?.data
                Log.d("VideoActivity", "Video recorded URI: $videoUri")
            }
        }

        btnRecordVideo.setOnClickListener {
            val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            recordVideoLauncher.launch(videoIntent)
        }

        btnPlayVideo.setOnClickListener {
            if (videoUri != null) {
                playVideo(videoUri!!)
            } else {
                Log.d("VideoActivity", "No video URI available to play")
            }
        }
    }

    private fun playVideo(uri: Uri) {
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    private fun checkPermissions() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
    }
}
