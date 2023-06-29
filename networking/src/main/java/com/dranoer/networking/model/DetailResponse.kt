package com.dranoer.networking.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("artObject")
    val artDetail: DetailObject,
)

data class DetailObject(
    @SerializedName("id")
    val id: String,

    @SerializedName("principalOrFirstMaker")
    val artist: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("webImage")
    val image: DetailImage? = null,
)

data class DetailImage(
    @SerializedName("guid")
    val guid: String,

    @SerializedName("url")
    val url: String,
)