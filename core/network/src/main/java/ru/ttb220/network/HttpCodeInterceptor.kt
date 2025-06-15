package ru.ttb220.network

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import ru.ttb220.network.exception.ClientErrorException
import ru.ttb220.network.exception.ForbiddenException
import ru.ttb220.network.exception.IncorrectInputFormatException
import ru.ttb220.network.exception.JsonDecodingException
import ru.ttb220.network.exception.NotFoundException
import ru.ttb220.network.exception.ServerErrorException
import ru.ttb220.network.exception.UnauthorizedException
import ru.ttb220.network.model.response.ErrorResponse
import javax.inject.Inject

class HttpCodeInterceptor @Inject constructor(
    private val json: Json
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) return response

        val bodyString = response.body?.string().orEmpty()
        val errorMessage = try {
            json.decodeFromString<ErrorResponse>(bodyString).error ?: "Unknown error"
        } catch (e: Exception) {
            throw JsonDecodingException(
                "Failed to parse error message"
            )
        }

        when (response.code) {
            400 -> throw IncorrectInputFormatException(
                code = 400,
                message = errorMessage,
                responseBody = bodyString
            )

            401 -> throw UnauthorizedException(
                code = 401,
                message = errorMessage,
                responseBody = bodyString
            )

            // в теории никогда не выбросится
            403 -> throw ForbiddenException(
                code = 403,
                message = errorMessage,
                responseBody = bodyString
            )

            404 -> throw NotFoundException(
                code = 404,
                message = errorMessage,
                responseBody = bodyString
            )

            in 400..499 -> throw ClientErrorException(
                code = response.code,
                message = errorMessage,
                responseBody = bodyString
            )

            in 500..599 -> throw ServerErrorException(
                code = response.code,
                message = errorMessage,
                responseBody = bodyString
            )
        }

        return response
    }
}
