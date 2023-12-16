package com.rifaqat.wallpaper.api

import com.rifaqat.wallpaper.model.Wallpaper

//This interface defines methods to handle responses and errors from the API.
interface WallpaperResponseListener {
    fun onFetch(response: Wallpaper, message:String)
    fun onError(message: String)
}