package com.example.playlistmaker

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.adapter.TracksAdapter
import com.example.playlistmaker.model.Track
import com.example.playlistmaker.model.TracksResponse
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearDrawable: Drawable
    private lateinit var searchDrawable: Drawable
    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var icProblem: ImageView
    private lateinit var tvProblem: TextView
    private lateinit var btnUpdate: Button
    private var searchQuery: String = ""

    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApi::class.java)

    private var listTrack = listOf<Track>()

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
        icProblem = findViewById(R.id.icProblem)
        tvProblem = findViewById(R.id.tvProblem)
        btnUpdate = findViewById(R.id.btnReconnect)

        searchEditText.setText(searchQuery)
        updateIcons(searchEditText.text)

        val onItemClickListener = OnItemClickListener { item ->
            val position = listTrack.indexOf(item)
        }

        recyclerView = findViewById<RecyclerView>(R.id.list_track)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tracksAdapter = TracksAdapter(listTrack, onItemClickListener)
        recyclerView.adapter = tracksAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchQuery = s?.toString().orEmpty()
                updateIcons(s)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        btnUpdate.setOnClickListener {
            val query = searchEditText.text?.toString().orEmpty()
            if (query.isNotEmpty()) {
                search(query)
            }
        }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = searchEditText.text?.toString().orEmpty()
                if (query.isNotEmpty()) {
                    search(query)
                }
                true
            } else {
                false
            }
        }

        searchEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchEditText.compoundDrawables[2] ?: return@setOnTouchListener false
                val x = event.x.toInt()
                val width = searchEditText.width
                val padding = searchEditText.paddingEnd
                val drawableWidth = drawableEnd.bounds.width()
                if (x >= width - padding - drawableWidth) {
                    searchEditText.text.clear()
                    tracksAdapter.updateList(emptyList())
                    hideKeyboard()
                    return@setOnTouchListener true
                }
            }
            false
        }

    }

    private fun search(query: String) {
        iTunesService.search(query).enqueue(object : Callback<TracksResponse> {
            override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
                if (response.isSuccessful) {
                    listTrack = response.body()?.results ?: emptyList()
                    if (listTrack.isNotEmpty()) {
                        icProblem.visibility = View.GONE
                        tvProblem.visibility = View.GONE
                        btnUpdate.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        tracksAdapter.updateList(listTrack)
                    } else {
                        showEmptyResult()
                    }
                } else {
                    showConnectionError()
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                showConnectionError()
            }
        })
    }

    private fun showEmptyResult() {
        recyclerView.visibility = View.GONE
        btnUpdate.visibility = View.GONE
        icProblem.setImageResource(R.drawable.ic_not_found_music)
        icProblem.visibility = View.VISIBLE
        tvProblem.text = getString(R.string.not_fault_music)
        tvProblem.visibility = View.VISIBLE
    }

    private fun showConnectionError() {
        btnUpdate.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        icProblem.setImageResource(R.drawable.ic_communication_problems)
        icProblem.visibility = View.VISIBLE
        tvProblem.text = getString(R.string.connection_problem)
        tvProblem.visibility = View.VISIBLE
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
