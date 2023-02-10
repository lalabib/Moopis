package com.latihan.lalabib.moopis.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.latihan.lalabib.moopis.data.MoopisRepository
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.utils.Resource

class HomeViewModel(private val moopisRepository: MoopisRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = moopisRepository.getMovie()
}