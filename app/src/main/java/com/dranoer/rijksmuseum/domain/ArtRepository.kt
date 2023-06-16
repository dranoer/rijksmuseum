package com.dranoer.rijksmuseum.domain

import com.dranoer.rijksmuseum.data.remote.WebService
import com.dranoer.rijksmuseum.data.remote.model.ArtResponse
import com.dranoer.rijksmuseum.ui.ArtItem
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val service: WebService,
) {

    suspend fun fetchArtList(): List<ArtItem> {
        val response = service.fetchArtList()
        return artMapper(artResponse = response)
    }

    private fun artMapper(artResponse: ArtResponse): List<ArtItem> {
        return artResponse.artObjects.map { item ->
            ArtItem(
                id = item.id,
                artist = item.artist ?: "",
                title = item.title ?: "",
                longTitle = item.longTitle ?: "",
                imageUrl = item.image?.url ?: "",
                headerImageUrl = item.headerImage?.url ?: "",
            )
        }
    }
}