package ru.ttb220.network.di

import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.ttb220.network.BuildConfig
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.RetrofitNetwork
import ru.ttb220.network.interceptor.AuthInterceptor
import ru.ttb220.network.interceptor.HttpCodeInterceptor
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BEARER_TOKEN = BuildConfig.BEARER_TOKEN

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Injects logging, auth and exception interceptors
     */
    @Provides
    @Singleton
    fun okHttpCallFactory(
        json: Json
    ): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(
                AuthInterceptor {
                    BEARER_TOKEN
                }
            )
            .addInterceptor(
                HttpCodeInterceptor(json)
            )
            .build()
    }

    @Provides
    fun providesRemoteDataSource(
        retrofitNetwork: RetrofitNetwork
    ): RemoteDataSource {
        return retrofitNetwork
    }
}