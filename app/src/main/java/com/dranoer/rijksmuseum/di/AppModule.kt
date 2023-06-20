package com.dranoer.rijksmuseum.di

import com.dranoer.rijksmuseum.data.remote.WebService
import com.dranoer.rijksmuseum.data.remote.mapper.ArtMapper
import com.dranoer.rijksmuseum.data.remote.mapper.DetailMapper
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.util.Const.BASE_URL
import com.dranoer.rijksmuseum.ui.util.Const.NETWORK_REQUEST_TIMEOUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRetrofit(): WebService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun provideArtMapper(): ArtMapper {
        return ArtMapper()
    }

    @Provides
    fun provideDetailMapper(): DetailMapper {
        return DetailMapper()
    }

    @Singleton
    @Provides
    fun provideRepository(
        webService: WebService,
        artMapper: ArtMapper,
        detailMapper: DetailMapper,
    ) = ArtRepository(service = webService, artMapper = artMapper, detailMapper = detailMapper)

    @Provides
    fun provideIODispatcher() = Dispatchers.IO
}