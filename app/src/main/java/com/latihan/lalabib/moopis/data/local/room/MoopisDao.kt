package com.latihan.lalabib.moopis.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

@Dao
interface MoopisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MoviesEntity)

    @Query("SELECT * From movie_entities")
    fun getAllMovie(): PagingSource<Int, MoviesEntity>

    @Transaction
    @Query("SELECT * From movie_entities WHERE id = :id")
    fun getDetailMovie(id: String): LiveData<MoviesEntity>

    @Update
    fun updateMovie(movie: MoviesEntity)

    @Query("DELETE From movie_entities")
    suspend fun deleteAll()
}