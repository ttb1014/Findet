package ru.ttb220.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.network.exception.ApiException
import ru.ttb220.network.exception.ClientErrorException
import ru.ttb220.network.exception.ForbiddenException
import ru.ttb220.network.exception.IncorrectInputFormatException
import ru.ttb220.network.exception.JsonDecodingException
import ru.ttb220.network.exception.NotFoundException
import ru.ttb220.network.exception.ServerErrorException
import ru.ttb220.network.exception.UnauthorizedException

fun <T> Flow<T>.wrapToSafeResult(): Flow<SafeResult<T>> =
    this
        .map<T, SafeResult<T>> {
            SafeResult.Success(it)
        }
        .catch { e ->
            val domainError: DomainError = when (e) {
                is ForbiddenException -> {
                    DomainError.Unknown(e.message)
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
                    DomainError.Unauthorized
                }

                is ClientErrorException -> {
                    DomainError.Unknown(e.message)
                }

                is ServerErrorException -> {
                    DomainError.ServerError
                }

                is ApiException -> {
                    DomainError.Unknown(e.message)
                }

                else -> {
                    DomainError.Unknown(e.message)
                }
            }

            emit(SafeResult.Failure(domainError))
        }