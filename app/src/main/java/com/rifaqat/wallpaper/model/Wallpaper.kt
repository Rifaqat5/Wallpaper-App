package com.rifaqat.wallpaper.model

data class Wallpaper(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Images>
)