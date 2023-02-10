package com.latihan.lalabib.moopis.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.local.room.MoopisDao


class LocalDataSource(private val moopisDao: MoopisDao) {

    fun getMovie(): DataSource.Factory<Int, MoviesEntity> = moopisDao.getMovie()

    fun insertMovie(movie: List<MoviesEntity>) = moopisDao.insertMovie(movie)

    fun getDetailMovie(id: String): LiveData<MoviesEntity> = moopisDao.getDetailMovie(id)

    fun updateMovie(movie: MoviesEntity) {
        moopisDao.updateMovie(movie)
    }

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(moopisDao: MoopisDao): LocalDataSource =
            instance ?: LocalDataSource(moopisDao)
    }
}