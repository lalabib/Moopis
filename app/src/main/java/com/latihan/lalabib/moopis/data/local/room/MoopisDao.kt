package com.latihan.lalabib.moopis.data.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

@Dao
interface MoopisDao {

    @Query("SELECT * From movie_entities")
    fun getMovie(): DataSource.Factory<Int, MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MoviesEntity>)
}