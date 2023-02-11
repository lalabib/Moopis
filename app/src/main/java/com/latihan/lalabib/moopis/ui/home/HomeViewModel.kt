package com.latihan.lalabib.moopis.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.latihan.lalabib.moopis.data.MoopisRepository
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

class HomeViewModel(moopisRepository: MoopisRepository): ViewModel() {

    val movie: LiveData<PagingData<MoviesEntity>> =
        moopisRepository.getAllMovies().cachedIn(viewModelScope)
}