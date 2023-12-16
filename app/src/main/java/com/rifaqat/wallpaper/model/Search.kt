package com.rifaqat.wallpaper.model

data class Search(
    val total_results:Int,
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Images>
)