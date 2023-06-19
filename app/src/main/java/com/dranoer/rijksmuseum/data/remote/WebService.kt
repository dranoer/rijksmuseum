package com.dranoer.rijksmuseum.data.remote

import com.dranoer.rijksmuseum.data.remote.model.ArtResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("collection")
    suspend fun fetchArtList(
        @Query("q") query: String = "",
        @Query("p") page: Int,
        @Query("ps") pageSize: Int = 10,
        @Query("key") key: String = "0fiuZFh4"
    ): ArtResponse
}