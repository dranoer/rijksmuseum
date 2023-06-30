package com.dranoer.rijksmuseum.networking.mapper

import com.dranoer.rijksmuseum.networking.model.DetailItem
import com.dranoer.rijksmuseum.networking.model.DetailResponse

class DetailMapper {
    fun map(response: DetailResponse): DetailItem {
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
}