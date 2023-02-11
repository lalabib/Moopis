package com.latihan.lalabib.moopis.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.latihan.lalabib.moopis.data.local.LocalDataSource
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.local.room.MoopisDatabase
import com.latihan.lalabib.moopis.data.remote.ApiResponse
import com.latihan.lalabib.moopis.data.remote.RemoteDataSource
import com.latihan.lalabib.moopis.data.remote.response.DetailMovieResponse
import com.latihan.lalabib.moopis.data.remote.response.ReviewsResponse
import com.latihan.lalabib.moopis.networking.ApiEndPoint
import com.latihan.lalabib.moopis.utils.AppExecutors
import com.latihan.lalabib.moopis.utils.Resource

class MoopisRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val database: MoopisDatabase,
    private val apiService: ApiEndPoint
) : MoopisDataSource {

    fun getAllMovies(): LiveData<PagingData<MoviesEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 8),
            remoteMediator = MoopisRemoteMediator(database, apiService),
            pagingSourceFactory = {
                //MoopisPagingSource(apiService)
                database.movieDao().getAllMovie()
            }
        ).liveData
    }

    override fun getDetailMovie(id: String): LiveData<Resource<MoviesEntity>> {
        return object : NetworkBoundResource<MoviesEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MoviesEntity> {
                return localDataSource.getDetailMovie(id)
            }

            override fun shouldFetch(data: MoviesEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override fun saveCallResult(data: DetailMovieResponse) {
                val movie = MoviesEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.voteAverage,
                    data.posterPath
                )
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getReview(id: String): LiveData<ReviewsResponse> {
        val reviews = MutableLiveData<ReviewsResponse>()
        remoteDataSource.getReview(id, object : RemoteDataSource.LoadReviewCallback {
            override fun reviewReceived(reviewResponse: ReviewsResponse) {
                reviews.postValue(reviewResponse)
            }
        })
        return reviews
    }

    companion object {
        @Volatile
        private var instance: MoopisRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors,
            database: MoopisDatabase,
            apiService: ApiEndPoint
        ): MoopisRepository =
            instance ?: synchronized(this) {
                instance ?: MoopisRepository(
                    remoteDataSource,
                    localDataSource,
                    appExecutors,
                    database,
                    apiService
                )
            }
    }
}