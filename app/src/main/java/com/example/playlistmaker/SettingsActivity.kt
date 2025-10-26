package com.example.playlistmaker

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backImage = findViewById<ImageView>(R.id.back)

        val shareLayout = findViewById<LinearLayout>(R.id.share)
        shareLayout.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.com/profile/android-developer-plus/")
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, getText(R.string.share_to))
            startActivity(chooser)
        }

        val writeToSupport = findViewById<LinearLayout>(R.id.writeToSupport)
        writeToSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:aleksey.sviatoho@yandex.by".toUri()
                putExtra(Intent.EXTRA_SUBJECT, getText(R.string.message_email))
                putExtra(Intent.EXTRA_TEXT, getText(R.string.text_email))
            }
            startActivity(intent)

        }

        val flatteringAgreement = findViewById<LinearLayout>(R.id.flatteringAgreement)
        flatteringAgreement.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/ru/"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }

        backImage.setOnClickListener {
            finish()
        }
    }
}