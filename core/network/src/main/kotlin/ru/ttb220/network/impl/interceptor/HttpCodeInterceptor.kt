package ru.ttb220.network.impl.interceptor

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import ru.ttb220.network.api.exception.ClientErrorException
import ru.ttb220.network.api.exception.ForbiddenException
import ru.ttb220.network.api.exception.IncorrectInputFormatException
import ru.ttb220.network.api.exception.JsonDecodingException
import ru.ttb220.network.api.exception.NotFoundException
import ru.ttb220.network.api.exception.ServerErrorException
import ru.ttb220.network.api.exception.UnauthorizedException
import ru.ttb220.network.api.model.response.ErrorResponseDto
import javax.inject.Inject

/**
 * Responsible for throwing correct exceptions whenever 400-599 code is received
 */
class HttpCodeInterceptor @Inject constructor(
    private val json: Json
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) return response

        val bodyString = response.body?.string().orEmpty()

        val errorMessage = try {
            json.decodeFromString<ErrorResponseDto>(bodyString).error ?: UNKNOWN_ERROR_MESSAGE
        } catch (e: SerializationException) {
            throw JsonDecodingException(
                DECODING_ERROR_MESSAGE,
                cause = e
            )
        }

        when (response.code) {
            400 -> throw IncorrectInputFormatException(
                message = errorMessage,
                responseBody = bodyString
            )

            401 -> throw UnauthorizedException(
                message = errorMessage,
                responseBody = bodyString
            )

            // в теории никогда не выбросится
            403 -> throw ForbiddenException(
                message = errorMessage,
                responseBody = bodyString
            )

            404 -> throw NotFoundException(
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

    companion object {
        private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
        private const val DECODING_ERROR_MESSAGE = "Failed to parse error message"
    }
}
