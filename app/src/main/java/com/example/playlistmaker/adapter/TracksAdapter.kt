package com.example.playlistmaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.playlistmaker.model.Track
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.OnItemClickListener
import com.example.playlistmaker.R
import com.example.playlistmaker.viewholder.TrackViewHolder

class TracksAdapter(
    private val tracks: List<Track>,
    private val onItemClickListener: OnItemClickListener
    ): RecyclerView.Adapter<TrackViewHolder> () {

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
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(tracks[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }


}
