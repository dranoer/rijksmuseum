package com.dranoer.rijksmuseum.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dranoer.rijksmuseum.data.remote.ArtPagingSource
import com.dranoer.rijksmuseum.data.remote.WebService
import com.dranoer.rijksmuseum.data.remote.model.ArtDetailResponse
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.DetailItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val service: WebService,
) {
    fun fetchArtList(query: String = ""): Flow<PagingData<ArtItem>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { ArtPagingSource(service, query) },
    ).flow

    suspend fun fetchArtDetail(id: String): DetailItem {
        val response = service.fetchArtDetail(id = id)
        return detailMapper(response = response)
    }

    private fun detailMapper(response: ArtDetailResponse): DetailItem {
        return response.artDetail.let { item ->
            DetailItem(
                id = item.id,
                artist = item.artist ?: "",
                title = item.title ?: "",
                description = item.description ?: "",
                imageUrl = item.image?.url ?: "",
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}