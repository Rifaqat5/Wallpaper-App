package com.rifaqat.wallpaper.api

import com.rifaqat.wallpaper.model.Search
import com.rifaqat.wallpaper.model.Wallpaper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchWallpaper{
    @Headers(
        "Accept: application/json",
        "Authorization: o1FhzZv6OXj4VZC70eKMaOWsuH0Puwvt2bjRe7u0PMZdBkoiv5AqkEnW"
    )

    @GET("search")
    fun searchWallpaper(@Query("query") query:String,@Query("page") page:String, @Query("per_page") perPage:String): Call<Search>
}