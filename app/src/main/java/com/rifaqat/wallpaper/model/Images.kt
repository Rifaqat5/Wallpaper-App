package com.rifaqat.wallpaper.model

import com.rifaqat.wallpaper.model.Src
import java.io.Serializable

data class Images(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: Src,
    val url: String,
    val width: Int
):Serializable