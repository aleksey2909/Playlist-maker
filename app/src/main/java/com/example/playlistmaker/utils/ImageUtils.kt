package com.example.playlistmaker.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R

fun Int.dp(context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()

fun ImageView.loadRounded(
    url: String?,
    radiusDp: Int = 8,
    placeholder: Int = R.drawable.placeholder
) {
    val radiusPx = radiusDp.dp(context)

    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .transform(CenterCrop(), RoundedCorners(radiusPx))
        .into(this)
}
