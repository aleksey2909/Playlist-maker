package com.example.playlistmaker.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.app.di.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.adapter.TracksAdapter
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.ui.SearchState
import com.example.playlistmaker.presentation.ui.SearchViewModel
import com.google.android.material.appbar.MaterialToolbar


class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchEditText: EditText
    private lateinit var clearDrawable: Drawable
    private lateinit var searchDrawable: Drawable
    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var icProblem: ImageView
    private lateinit var tvProblem: TextView
    private lateinit var btnUpdate: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MaterialToolbar>(R.id.back).setNavigationOnClickListener { finish() }

        initViews()
        initViewModel()
        initRecycler()
        initListeners()

        viewModel.restoreState()
    }

    private fun initViews() {
        searchEditText = findViewById(R.id.searchEditText)
        clearDrawable = ContextCompat.getDrawable(this, R.drawable.ic_clear_16x16)!!
        searchDrawable = ContextCompat.getDrawable(this, R.drawable.ic_search_icon)!!
        icProblem = findViewById(R.id.icProblem)
        tvProblem = findViewById(R.id.tvProblem)
        btnUpdate = findViewById(R.id.btnReconnect)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.list_track)
    }

    private fun initViewModel() {
        viewModel = Creator.provideSearchViewModel(this)
        viewModel.state.observe(this) { render(it) }
    }

    private fun initRecycler() {
        tracksAdapter = TracksAdapter(emptyList()) { track: Track ->
            startActivity(Intent(this, TrackActivity::class.java).apply {
                putExtra("track", track)
            })
            viewModel.onTrackClicked(track)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tracksAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateIcons(s)
                viewModel.onQueryChanged(s?.toString().orEmpty())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnUpdate.setOnClickListener {
            viewModel.onActionButtonClicked()
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
                    hideKeyboard()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Default -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                icProblem.visibility = View.GONE
                tvProblem.visibility = View.GONE
                btnUpdate.visibility = View.GONE
            }
            is SearchState.Loading -> {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                icProblem.visibility = View.GONE
                tvProblem.visibility = View.GONE
                btnUpdate.visibility = View.GONE
            }
            is SearchState.Content -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                tracksAdapter.updateList(state.tracks)
                icProblem.visibility = View.GONE
                tvProblem.visibility = View.GONE
                btnUpdate.visibility = View.GONE
            }
            is SearchState.Empty -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                icProblem.setImageResource(R.drawable.ic_not_found_music)
                icProblem.visibility = View.VISIBLE
                tvProblem.text = getString(R.string.not_fault_music)
                tvProblem.visibility = View.VISIBLE
                btnUpdate.visibility = View.GONE
            }
            is SearchState.Error -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                icProblem.setImageResource(R.drawable.ic_communication_problems)
                icProblem.visibility = View.VISIBLE
                tvProblem.text = getString(R.string.connection_problem)
                tvProblem.visibility = View.VISIBLE
                btnUpdate.text = getString(R.string.update)
                btnUpdate.visibility = View.VISIBLE
            }
            is SearchState.History -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                tracksAdapter.updateList(state.tracks)
                icProblem.visibility = View.GONE
                tvProblem.text = getString(R.string.you_where_looking)
                tvProblem.visibility = View.VISIBLE
                btnUpdate.text = getString(R.string.clear_history)
                btnUpdate.visibility = View.VISIBLE
            }
        }
    }

    private fun updateIcons(text: Editable?) {
        val endIcon = if (!text.isNullOrEmpty()) clearDrawable else null
        searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
            searchDrawable, null, endIcon, null
        )

    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}
