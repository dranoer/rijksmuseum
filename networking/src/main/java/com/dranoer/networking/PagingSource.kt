package com.dranoer.networking

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dranoer.networking.mapper.ArtMapper
import com.dranoer.networking.model.ArtItem
import java.lang.Exception

class PagingSource(
    private val service: WebService,
    private val query: String,
    private val artMapper: ArtMapper,
) : PagingSource<Int, ArtItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtItem> {
        val page = params.key ?: 1
        return try {
            val response = service.fetchArtList(query, page)
            val artItems = artMapper.map(artResponse = response)
            LoadResult.Page(
                data = artItems,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (artItems.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}