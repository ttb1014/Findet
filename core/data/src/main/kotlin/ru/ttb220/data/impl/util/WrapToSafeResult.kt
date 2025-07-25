package ru.ttb220.data.impl.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.network.api.exception.ApiException
import ru.ttb220.network.api.exception.ClientErrorException
import ru.ttb220.network.api.exception.ForbiddenException
import ru.ttb220.network.api.exception.IncorrectInputFormatException
import ru.ttb220.network.api.exception.JsonDecodingException
import ru.ttb220.network.api.exception.NotFoundException
import ru.ttb220.network.api.exception.ServerErrorException
import ru.ttb220.network.api.exception.UnauthorizedException

/**
 * Wraps either data to Success or exception to Failure with mapping to available domain errors
 */
fun <T> Flow<T>.wrapToSafeResult(): Flow<SafeResult<T>> =
    this
        .map<T, SafeResult<T>> {
            SafeResult.Success(it)
        }
        .catch { e ->
            val domainError: DomainError = when (e) {
                is ForbiddenException -> {
                    DomainError.UnknownError(e.message)
                }

                is IncorrectInputFormatException -> {
                    DomainError.InvalidInput
                }

                is JsonDecodingException -> {
                    DomainError.InvalidInput
                }

                is NotFoundException -> {
                    DomainError.NotFound
                }

                is UnauthorizedException -> {
                    DomainError.ApiError
                }

                is ClientErrorException -> {
                    DomainError.UnknownError(e.message)
                }

                is ServerErrorException -> {
                    DomainError.ApiError
                }

                is ApiException -> {
                    DomainError.UnknownError(e.message)
                }

                else -> {
                    DomainError.UnknownError(e.message)
                }
            }

            emit(SafeResult.Failure(domainError))
        }