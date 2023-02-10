package com.latihan.lalabib.moopis.networking

import com.latihan.lalabib.moopis.data.remote.response.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {

    @GET("movie/now_playing")
    fun getMovie(@Query("api_key") apiKey: String): Call<MoviesResponse>
}