package com.latihan.lalabib.moopis.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.latihan.lalabib.moopis.data.MoopisRepository
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.remote.response.ReviewsResponse
import com.latihan.lalabib.moopis.data.remote.response.VideosResponse
import com.latihan.lalabib.moopis.utils.Resource

class DetailViewModel(private val moopisRepository: MoopisRepository) : ViewModel() {

    private val movieId = MutableLiveData<String>()

    fun setMoviesData(id: String) {
        movieId.value = id
    }

    var detailMovie: LiveData<Resource<MoviesEntity>> =
        Transformations.switchMap(movieId) { movieId ->
            moopisRepository.getDetailMovie(movieId)
        }

    fun setReviewsData(id: String) {
        movieId.value = id
    }

    var reviewData: LiveData<ReviewsResponse> =
        Transformations.switchMap(movieId) { movieId ->
            moopisRepository.getReview(movieId)
        }

    fun setVideosData(id: String) {
        movieId.value = id
    }

    val videoData: LiveData<VideosResponse> =
        Transformations.switchMap(movieId) { movieId ->
            moopisRepository.getVideo(movieId)
        }
}