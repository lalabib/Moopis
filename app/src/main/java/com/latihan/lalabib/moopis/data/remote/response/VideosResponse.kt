package com.latihan.lalabib.moopis.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("results")
    val results: ArrayList<VideosEntity>
)

data class VideosEntity(
    @SerializedName("name")
    val name: String?,
    @SerializedName("key")
    val key: String?
)