package com.rifaqat.wallpaper.api

import com.rifaqat.wallpaper.model.Wallpaper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//This interface defines the structure of the API requests using annotations from Retrofit.
interface CallWallpaperList {
    @Headers(
        "Accept: application/json",
        "Authorization: o1FhzZv6OXj4VZC70eKMaOWsuH0Puwvt2bjRe7u0PMZdBkoiv5AqkEnW"
    )

    @GET("curated/")
    fun getWallpapersList(@Query("page") page:String, @Query("per_page") perPage:String): Call<Wallpaper>
}