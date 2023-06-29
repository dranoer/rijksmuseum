package com.dranoer.networking.mapper

import com.dranoer.networking.model.DetailItem
import com.dranoer.networking.model.DetailResponse

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