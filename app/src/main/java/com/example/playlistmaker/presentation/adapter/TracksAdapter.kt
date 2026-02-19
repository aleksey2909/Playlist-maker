package com.example.playlistmaker.presentation.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.playlistmaker.domain.models.Track
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.presentation.ui.OnItemClickListener
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.viewholder.TrackViewHolder

class TracksAdapter(
    private var tracks: List<Track>,
    private val onItemClickListener: OnItemClickListener
    ): RecyclerView.Adapter<TrackViewHolder> () {

        private var isClickEnable = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tracks, parent, false)
            return TrackViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TrackViewHolder,
        position: Int
    ) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            if (isClickEnable) {
                isClickEnable = false
                onItemClickListener.onItemClick(tracks[holder.adapterPosition])

                Handler(Looper.getMainLooper()).postDelayed({
                    isClickEnable = true
                }, 1000L)
            }
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Track>) {
        tracks = newList
        notifyDataSetChanged()
    }

}
