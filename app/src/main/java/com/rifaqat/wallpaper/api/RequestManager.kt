package com.rifaqat.wallpaper.api

import android.content.Context
import android.widget.Toast
import com.rifaqat.wallpaper.model.Search
import com.rifaqat.wallpaper.model.Wallpaper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//This class is responsible for making API requests using Retrofit.
class RequestManager(val context: Context) {
    // Create a Retrofit instance with a base URL and Gson converter factory
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.pexels.com/v1/") // Base URL for the API
        .addConverterFactory(GsonConverterFactory.create()) // Gson converter factory for JSON parsing
        .build() // Build the Retrofit instance

    fun getWallpapers(listener: WallpaperResponseListener, page:String){
        val callWallpaperList=retrofit.create(CallWallpaperList::class.java)
        val call=callWallpaperList.getWallpapersList(page,"18")

        call.enqueue(object : Callback<Wallpaper> {
            override fun onResponse(call: Call<Wallpaper>, response: Response<Wallpaper>) {
                // Handle the API response here
                if (response.isSuccessful) {
                    response.body()?.let { listener.onFetch(it,response.message()) }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Wallpaper>, t: Throwable) {
                t.message?.let { listener.onError(it) }
            }
        })
    }
    fun searchWallpapers(listener: SearchResponseListener, page:String, query:String){
        val searchWallpaper=retrofit.create(SearchWallpaper::class.java)
        val call=searchWallpaper.searchWallpaper(query,page,"18")

        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                // Handle the API response here
                if (response.isSuccessful) {
                    response.body()?.let { listener.onFetch(it,response.message()) }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                t.message?.let { listener.onError(it) }
            }
        })
    }
}
