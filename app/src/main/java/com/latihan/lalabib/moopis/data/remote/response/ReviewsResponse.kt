package com.latihan.lalabib.moopis.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("results")
    val results: ArrayList<ReviewsEntity>
)

data class ReviewsEntity(
    @SerializedName("author")
    val author: String?,
    @SerializedName("author_details")
    val authorDetail: AuthorDetail? = null,
    @SerializedName("content")
    val review: String?
)

data class AuthorDetail(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: String?
)
