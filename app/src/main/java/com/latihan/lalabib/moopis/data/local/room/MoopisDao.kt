package com.latihan.lalabib.moopis.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

@Dao
interface MoopisDao {

    @Query("SELECT * From movie_entities")
    fun getMovie(): DataSource.Factory<Int, MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MoviesEntity>)

    @Transaction
    @Query("SELECT * From movie_entities WHERE id = :id")
    fun getDetailMovie(id: String): LiveData<MoviesEntity>

    @Update
    fun updateMovie(movie: MoviesEntity)
}