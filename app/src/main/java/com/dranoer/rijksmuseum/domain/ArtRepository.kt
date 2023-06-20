package com.dranoer.rijksmuseum.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dranoer.rijksmuseum.data.remote.PagingSource
import com.dranoer.rijksmuseum.data.remote.WebService
import com.dranoer.rijksmuseum.data.remote.mapper.ArtMapper
import com.dranoer.rijksmuseum.data.remote.mapper.DetailMapper
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.DetailItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val service: WebService,
    private val artMapper: ArtMapper,
    private val detailMapper: DetailMapper,
) {
    fun fetchArtList(query: String = ""): Flow<PagingData<ArtItem>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {
            PagingSource(
                service = service,
                query = query,
                artMapper = artMapper
            )
        },
    ).flow

    suspend fun fetchArtDetail(id: String): DetailItem {
        val response = service.fetchArtDetail(id = id)
        return detailMapper.map(response = response)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}