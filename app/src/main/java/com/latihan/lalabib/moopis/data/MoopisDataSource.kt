package com.latihan.lalabib.moopis.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.remote.response.ReviewsResponse
import com.latihan.lalabib.moopis.utils.Resource

interface MoopisDataSource {

    fun getDetailMovie(id: String): LiveData<Resource<MoviesEntity>>

    fun getReview(id: String): LiveData<ReviewsResponse>
}