package com.example.playlistmaker

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearDrawable: Drawable
    private lateinit var searchDrawable: Drawable
    private var searchQuery: String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val back = findViewById<MaterialToolbar>(R.id.back)
        back.setNavigationOnClickListener {
            finish()
        }

        searchEditText = findViewById(R.id.searchEditText)
        clearDrawable = ContextCompat.getDrawable(this, R.drawable.ic_clear_16x16)!!
        searchDrawable = ContextCompat.getDrawable(this, R.drawable.ic_search_icon)!!

        searchEditText.setText(searchQuery)
        updateIcons(searchEditText.text)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchQuery = s?.toString().orEmpty()
                updateIcons(s)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        searchEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchEditText.compoundDrawables[2] ?: return@setOnTouchListener false
                val x = event.x.toInt()
                val width = searchEditText.width
                val padding = searchEditText.paddingEnd
                val drawableWidth = drawableEnd.bounds.width()
                if (x >= width - padding - drawableWidth) {
                    searchEditText.text.clear()
                    hideKeyboard()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_QUERY", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchQuery = savedInstanceState.getString("SEARCH_QUERY").orEmpty()
        searchEditText.setText(searchQuery)
        updateIcons(searchEditText.text)
    }

    private fun updateIcons(text: Editable?) {
        val endIcon = if (!text.isNullOrEmpty()) clearDrawable else null
        searchEditText.setCompoundDrawablesWithIntrinsicBounds(
            searchDrawable, null, endIcon, null
        )
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }


}
