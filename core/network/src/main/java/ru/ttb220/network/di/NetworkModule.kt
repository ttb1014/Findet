package ru.ttb220.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import ru.ttb220.network.AuthInterceptor
import ru.ttb220.network.BuildConfig
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.RetrofitNetwork
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val BEARER_TOKEN = BuildConfig.BEARER_TOKEN

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor {
                    BEARER_TOKEN
                }
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