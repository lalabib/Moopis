package com.latihan.lalabib.moopis.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.latihan.lalabib.moopis.data.local.LocalDataSource
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.remote.ApiResponse
import com.latihan.lalabib.moopis.data.remote.RemoteDataSource
import com.latihan.lalabib.moopis.data.remote.response.DetailMovieResponse
import com.latihan.lalabib.moopis.data.remote.response.MoviesResponse
import com.latihan.lalabib.moopis.utils.AppExecutors
import com.latihan.lalabib.moopis.utils.Resource

class MoopisRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MoopisDataSource {

    override fun getMovie(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, MoviesResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(8)
                    .setPageSize(8)
                    .build()

                return LivePagedListBuilder(localDataSource.getMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                //data == null || data.isEmpty()
                true //replace it with true if you want to always retrieve data from the internet

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                remoteDataSource.getMovie()

            override fun saveCallResult(data: MoviesResponse) {
                localDataSource.insertMovie(data.results)
            }
        }.asLiveData()
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

    companion object {
        @Volatile
        private var instance: MoopisRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MoopisRepository =
            instance ?: synchronized(this) {
                instance ?: MoopisRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }
}