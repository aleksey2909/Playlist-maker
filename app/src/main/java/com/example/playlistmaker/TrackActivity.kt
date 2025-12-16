package com.example.playlistmaker

import android.os.Bundle
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.model.Track
import com.example.playlistmaker.utils.loadRounded
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson

class TrackActivity : AppCompatActivity() {
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

        val json = intent.getStringExtra("track_json")
        val track = Gson().fromJson(json, Track::class.java)

        val imageView = findViewById<ImageView>(R.id.placeholder)

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
    }
}