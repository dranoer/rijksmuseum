package com.dranoer.rijksmuseum.data.remote.model

import kotlinx.serialization.SerialName

data class ArtResponse(
    @SerialName("artObjects")
    val artObjects: List<ArtObject>,
)

data class ArtObject(
    @SerialName("id")
    val id: String,

    @SerialName("principalOrFirstMaker")
    val artist: String,

    @SerialName("title")
    val title: String? = null,

    @SerialName("longTitle")
    val longTitle: String? = null,

    @SerialName("webImage")
    val image: Image? = null,

    @SerialName("headerImage")
    val headerImage: HeaderImage? = null,
)

data class Image(
    @SerialName("guid")
    val guid: String,

    @SerialName("url")
    val url: String,
)

data class HeaderImage(
    @SerialName("guid")
    val guid: String,

    @SerialName("url")
    val url: String,
)