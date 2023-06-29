package com.dranoer.networking.model

import com.google.gson.annotations.SerializedName

data class ArtResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObject>,
)

data class ArtObject(
    @SerializedName("id")
    val id: String,

    @SerializedName("objectNumber")
    val objectNumber: String,

    @SerializedName("principalOrFirstMaker")
    val artist: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("longTitle")
    val description: String? = null,

    @SerializedName("webImage")
    val image: ArtImage? = null,

    @SerializedName("headerImage")
    val headerImage: ArtHeaderImage? = null,
)

data class ArtImage(
    @SerializedName("guid")
    val guid: String,

    @SerializedName("url")
    val url: String,
)

data class ArtHeaderImage(
    @SerializedName("guid")
    val guid: String,

    @SerializedName("url")
    val url: String,
)