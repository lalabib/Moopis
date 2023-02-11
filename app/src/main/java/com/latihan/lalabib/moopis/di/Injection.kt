package com.latihan.lalabib.moopis.di

import android.content.Context
import com.latihan.lalabib.moopis.data.MoopisRepository
import com.latihan.lalabib.moopis.data.local.LocalDataSource
import com.latihan.lalabib.moopis.data.local.room.MoopisDatabase
import com.latihan.lalabib.moopis.data.remote.RemoteDataSource
import com.latihan.lalabib.moopis.networking.ApiConfig
import com.latihan.lalabib.moopis.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MoopisRepository {

        val database = MoopisDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        val apiService = ApiConfig.getApiEndPoint()

        return MoopisRepository.getInstance(remoteDataSource, localDataSource, appExecutors, database, apiService)
    }
}