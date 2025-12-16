package com.example.playlistmaker.viewholder

import android.content.res.Resources
import android.text.Html
import android.text.format.DateUtils
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.model.Track
import com.example.playlistmaker.utils.loadRounded
import kotlin.math.roundToInt

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackTitle)
    private val artistName: TextView = itemView.findViewById(R.id.tvArtist)
    private val trackTime: TextView = itemView.findViewById(R.id.tvDuration)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.ivAlbumArt)

    fun bind(item: Track) {
        trackName.text = item.trackName.trim()
        artistName.text = item.artistName.trim()
        artistName.requestLayout()
        trackTime.text = DateUtils.formatElapsedTime(item.trackTimeMillis / 1000)
        artworkUrl100.loadRounded(item.artworkUrl100,2, R.drawable.placeholder)
    }
}
