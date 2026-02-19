package com.example.playlistmaker.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import com.example.playlistmaker.app.di.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.ui.SettingsViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitcher: SwitchMaterial
    private lateinit var viewModel: SettingsViewModel

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

        viewModel = Creator.provideSettingsViewModel(this)

        val back = findViewById<MaterialToolbar>(R.id.back)
        themeSwitcher = findViewById(R.id.themeSwitcher)

        back.setNavigationOnClickListener { finish() }

        viewModel.darkTheme.observe(this) { isDark ->
            themeSwitcher.isChecked = isDark

            val mode = if (isDark) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }

        initShare()
        initSupport()
        initAgreement()
    }

    private fun initShare() {
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
    }

    private fun initSupport() {
        val writeToSupport = findViewById<MaterialTextView>(R.id.writeToSupport)
        writeToSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:aleksey.sviatoho@yandex.by".toUri()
                putExtra(Intent.EXTRA_SUBJECT, getText(R.string.message_email))
                putExtra(Intent.EXTRA_TEXT, getText(R.string.text_email))
            }
            startActivity(intent)
        }
    }

    private fun initAgreement() {
        val flatteringAgreement = findViewById<MaterialTextView>(R.id.flatteringAgreement)
        flatteringAgreement.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/ru/"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }
    }
}