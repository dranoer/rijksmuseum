package com.dranoer.networking

import com.dranoer.networking.model.ArtResponse
import com.dranoer.networking.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("collection")
    suspend fun fetchArtList(
        @Query("q") query: String = "",
        @Query("p") page: Int,
        @Query("ps") pageSize: Int = 10,
        @Query("key") key: String = API_KEY
    ): ArtResponse

    @GET("collection/{object-number}")
    suspend fun fetchArtDetail(
        @Path("object-number") id: String,
        @Query("key") key: String = API_KEY
    ): DetailResponse

    companion object {
        const val API_KEY = "0fiuZFh4"
    }
}