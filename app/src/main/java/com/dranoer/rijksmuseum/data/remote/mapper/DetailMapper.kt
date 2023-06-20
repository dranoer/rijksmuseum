package com.dranoer.rijksmuseum.data.remote.mapper

import com.dranoer.rijksmuseum.data.remote.model.DetailResponse
import com.dranoer.rijksmuseum.ui.DetailItem

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