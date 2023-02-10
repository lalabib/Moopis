package com.latihan.lalabib.moopis.networking

import com.latihan.lalabib.moopis.data.remote.response.DetailMovieResponse
import com.latihan.lalabib.moopis.data.remote.response.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {

    @GET("movie/popular")
    fun getMovie(@Query("api_key") apiKey: String): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String): Call<DetailMovieResponse>
}