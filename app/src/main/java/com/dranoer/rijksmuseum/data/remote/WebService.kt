package com.dranoer.rijksmuseum.data.remote

import com.dranoer.rijksmuseum.data.remote.model.ArtResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("collection")
    suspend fun fetchArtList(
        @Query("key") key: String = "0fiuZFh4"
    ) : ArtResponse
}