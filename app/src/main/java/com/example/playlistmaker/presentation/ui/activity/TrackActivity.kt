package com.example.playlistmaker.presentation.ui.activity

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.PlayerState
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.utils.loadRounded
import com.google.android.material.appbar.MaterialToolbar
import kotlin.math.ceil

class TrackActivity : AppCompatActivity() {

    private var playerState = PlayerState.DEFAULT

    private lateinit var time: TextView
    private lateinit var play: ImageView
    private var mediaPlayer = MediaPlayer()

    private val handler = Handler()
    private val updateTime = object : Runnable {
        @SuppressLint("DefaultLocale")
        override fun run() {
            if (mediaPlayer.isPlaying) {
                val currentPosition = ceil(mediaPlayer.currentPosition / 1000.0).toInt()
                time.text = String.format("0:%02d", currentPosition)
                handler.postDelayed(this, 200)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val back = findViewById<MaterialToolbar>(R.id.back)
        back.setNavigationOnClickListener {
            finish()
        }

        val track = intent.getParcelableExtra<Track>("track")!!

        val imageView = findViewById<ImageView>(R.id.placeholder)

        time = findViewById(R.id.time)
        play = findViewById(R.id.btnPlay)
        preparePlayer(track.previewUrl)

        val trackTitle  = findViewById<TextView>(R.id.trackTitle)
        val artistTitle = findViewById<TextView>(R.id.groupTitle)
        val duration = findViewById<TextView>(R.id.duration)
        val album = findViewById<TextView>(R.id.album)
        val releaseYear = findViewById<TextView>(R.id.year)
        val genre = findViewById<TextView>(R.id.genre)
        val country = findViewById<TextView>(R.id.country)


        trackTitle.text = track.trackName
        artistTitle.text = track.artistName
        duration.text = DateUtils.formatElapsedTime(track.trackTimeMillis / 1000)
        album.text = track.collectionName
        releaseYear.text = track.releaseDate.take(4)
        genre.text = track.primaryGenreName
        country.text = track.country

        val highResUrl = track.artworkUrl100.replace("100x100bb", "512x512bb")

        imageView.loadRounded(highResUrl, 8, R.drawable.ic_placeholder_312x312)

        play.setOnClickListener {
            playbackControl()
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
        handler.removeCallbacks(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(updateTime)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = PlayerState.PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            play.setImageResource(R.drawable.ic_button_play)
            playerState = PlayerState.PREPARED
        }
    }

    private fun playbackControl() {
        when(playerState) {
            PlayerState.PLAYING -> {
                pausePlayer()
            }
            PlayerState.PREPARED, PlayerState.PAUSED -> {
                startPlayer()
            }

            else -> {}
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        play.setImageResource(R.drawable.ic_button_pause)
        playerState = PlayerState.PLAYING
        handler.post(updateTime)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        play.setImageResource(R.drawable.ic_button_play)
        playerState = PlayerState.PAUSED
        handler.removeCallbacks(updateTime)
    }


}