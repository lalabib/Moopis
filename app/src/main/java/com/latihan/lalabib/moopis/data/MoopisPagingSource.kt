package com.latihan.lalabib.moopis.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.latihan.lalabib.moopis.networking.ApiEndPoint
import com.latihan.lalabib.moopis.BuildConfig.apiKey
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

/*class MoopisPagingSource (private val apiService: ApiEndPoint) : PagingSource<Int, MoviesEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesEntity> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllMovie(apiKey, page, params.loadSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}*/