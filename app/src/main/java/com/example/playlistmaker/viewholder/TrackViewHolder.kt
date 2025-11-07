package com.example.playlistmaker.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.model.Track

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackTitle)
    private val artistName: TextView = itemView.findViewById(R.id.tvArtist)
    private val trackTime: TextView = itemView.findViewById(R.id.tvDuration)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.ivAlbumArt)

    fun bind(item: Track) {
        trackName.text = item.trackName
        artistName.text = item.artistName
        trackTime.text = item.trackTime
        Glide.with(itemView.context)
            .load(item.artworkUrl100)
            .placeholder(R.drawable.media)
            .error(R.drawable.media)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(artworkUrl100)
    }
}
