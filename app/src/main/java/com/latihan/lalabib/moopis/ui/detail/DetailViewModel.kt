package com.latihan.lalabib.moopis.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.latihan.lalabib.moopis.data.MoopisRepository
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
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
}