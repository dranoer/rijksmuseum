package com.dranoer.rijksmuseum.ui

data class ArtGroup(
    val author: String,
    val artItems: List<ArtItem>,
)

data class ArtItem(
    val id: String,
    val artist: String,
    val title: String,
    val longTitle: String,
    val imageUrl: String,
    val headerImageUrl: String,
)