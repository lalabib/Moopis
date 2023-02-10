package com.latihan.lalabib.moopis.data.remote.response

import com.google.gson.annotations.SerializedName
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

data class MoviesResponse(
    @field:SerializedName("results")
    val results: ArrayList<MoviesEntity>
)
