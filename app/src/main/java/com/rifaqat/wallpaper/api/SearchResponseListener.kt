package com.rifaqat.wallpaper.api

import com.rifaqat.wallpaper.model.Search

interface SearchResponseListener {
    fun onFetch(response:Search,message:String)
    fun onError(message: String)
}