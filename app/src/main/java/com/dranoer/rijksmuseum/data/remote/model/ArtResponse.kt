package com.dranoer.rijksmuseum.data.remote.model

import com.google.gson.annotations.SerializedName

data class ArtResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObject>,
)

data class ArtObject(
    @SerializedName("id")
    val id: String,

    @SerializedName("principalOrFirstMaker")
    val artist: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("longTitle")
    val longTitle: String? = null,

    @SerializedName("webImage")
    val image: Image? = null,

    @SerializedName("headerImage")
    val headerImage: HeaderImage? = null,
)

data class Image(
    @SerializedName("guid")
    val guid: String,

    @SerializedName("url")
    val url: String,
)

data class HeaderImage(
    @SerializedName("guid")
    val guid: String,

    @SerializedName("url")
    val url: String,
)