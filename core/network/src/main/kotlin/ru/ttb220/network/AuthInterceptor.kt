package ru.ttb220.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Responsible for authorising requests to the backend through the
 * Bearer token injection
 */
class AuthInterceptor(private val tokenProvider: () -> String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}