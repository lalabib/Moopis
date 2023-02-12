package com.latihan.lalabib.moopis.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.latihan.lalabib.moopis.networking.ApiConfig
import com.latihan.lalabib.moopis.BuildConfig.apiKey
import com.latihan.lalabib.moopis.data.remote.response.DetailMovieResponse
import com.latihan.lalabib.moopis.data.remote.response.ReviewsResponse
import com.latihan.lalabib.moopis.data.remote.response.VideosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getDetailMovie(id: String): LiveData<ApiResponse<DetailMovieResponse>> {
        val resultDetailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        ApiConfig.getApiEndPoint().getDetailMovie(id, apiKey)
            .enqueue(object : Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultDetailMovie.value = ApiResponse.success(it) }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        return resultDetailMovie
    }

    fun getReview(id: String, callback: LoadReviewCallback) {
        ApiConfig.getApiEndPoint().getReview(id, apiKey).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.reviewReceived(it) }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getVideo(id: String, callback: LoadVideoCallback) {
        ApiConfig.getApiEndPoint().getVideo(id, apiKey).enqueue(object : Callback<VideosResponse> {
            override fun onResponse(
                call: Call<VideosResponse>,
                response: Response<VideosResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.videoReceived(it) }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<VideosResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    interface LoadReviewCallback {
        fun reviewReceived(reviewResponse: ReviewsResponse)
    }

    interface LoadVideoCallback {
        fun videoReceived(videosResponse: VideosResponse)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }

        private const val TAG = "RemoteData"
    }
}