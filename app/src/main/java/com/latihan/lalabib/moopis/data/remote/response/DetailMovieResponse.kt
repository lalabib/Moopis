package com.latihan.lalabib.moopis.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: String,
    @SerializedName("poster_path")
    val posterPath: String
)
