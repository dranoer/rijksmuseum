package com.dranoer.rijksmuseum.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dranoer.rijksmuseum.data.remote.model.ArtResponse
import com.dranoer.rijksmuseum.ui.ArtItem
import java.lang.Exception

class ArtPagingSource(
    private val service: WebService,
    private val query: String,
) : PagingSource<Int, ArtItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtItem> {
        val page = params.key ?: 1
        return try {
            val response = service.fetchArtList(query, page)
            val artItems = artMapper(artResponse = response)
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

    private fun artMapper(artResponse: ArtResponse): List<ArtItem> {
        return artResponse.artObjects.map { item ->
            ArtItem(
                id = item.id,
                objectNumber = item.objectNumber,
                artist = item.artist ?: "",
                title = item.title ?: "",
                description = item.description ?: "",
                imageUrl = item.image?.url ?: "",
                headerImageUrl = item.headerImage?.url ?: "",
            )
        }
    }
}