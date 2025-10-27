package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val back = findViewById<MaterialToolbar>(R.id.back)

        back.setNavigationOnClickListener {
            finish()
        }

        val share = findViewById<MaterialTextView>(R.id.share)
        share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.com/profile/android-developer-plus/")
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, getText(R.string.share_to))
            startActivity(chooser)
        }

        val writeToSupport = findViewById<MaterialTextView>(R.id.writeToSupport)
        writeToSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:aleksey.sviatoho@yandex.by".toUri()
                putExtra(Intent.EXTRA_SUBJECT, getText(R.string.message_email))
                putExtra(Intent.EXTRA_TEXT, getText(R.string.text_email))
            }
            startActivity(intent)

        }

        val flatteringAgreement = findViewById<MaterialTextView>(R.id.flatteringAgreement)
        flatteringAgreement.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/ru/"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }
    }
}