package com.dranoer.rijksmuseum.data.remote.mapper

import com.dranoer.rijksmuseum.data.remote.model.ArtResponse
import com.dranoer.rijksmuseum.ui.ArtItem

class ArtMapper {
    fun map(artResponse: ArtResponse): List<ArtItem> {
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