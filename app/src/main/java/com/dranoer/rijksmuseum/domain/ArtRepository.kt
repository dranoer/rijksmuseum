package com.dranoer.rijksmuseum.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dranoer.rijksmuseum.data.remote.ArtPagingSource
import com.dranoer.rijksmuseum.data.remote.WebService
import com.dranoer.rijksmuseum.ui.ArtItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val service: WebService,
) {
    fun fetchArtList(query: String = ""): Flow<PagingData<ArtItem>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { ArtPagingSource(service, query) },
    ).flow

    companion object {
        private const val PAGE_SIZE = 20
    }
}