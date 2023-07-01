package com.dranoer.rijksmuseum.networking.di

import com.dranoer.rijksmuseum.networking.PagingSource
import com.dranoer.rijksmuseum.networking.WebService
import com.dranoer.rijksmuseum.networking.mapper.ArtMapper
import com.dranoer.rijksmuseum.networking.mapper.DetailMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    private const val BASE_URL = "https://www.rijksmuseum.nl/api/nl/"
    private const val NETWORK_REQUEST_TIMEOUT_SECONDS = 15L

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideWebService(retrofit: Retrofit): WebService =
        retrofit.create(WebService::class.java)

    @Provides
    fun provideArtMapper(): ArtMapper = ArtMapper()

    @Provides
    fun provideDetailMapper(): DetailMapper = DetailMapper()

    @Provides
    fun providePagingSource(
        service: WebService,
        query: String,
        artMapper: ArtMapper
    ): PagingSource {
        return PagingSource(service, query, artMapper)
    }
}