package com.dranoer.networking.model

data class ArtGroup(
    val author: String,
    val artItems: List<ArtItem>,
)

data class ArtItem(
    val id: String,
    val objectNumber: String,
    val artist: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val headerImageUrl: String,
)