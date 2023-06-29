package com.dranoer.networking.mapper

import com.dranoer.networking.model.ArtItem
import com.dranoer.networking.model.ArtResponse

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