package ru.ttb220.model

/**
 * Lists all available errors that should be handled differently on UI
 */
sealed interface DomainError {

    data object NoInternet : DomainError

    data object InvalidInput : DomainError

    data object ApiError : DomainError

    data object NotFound : DomainError

    data class UnknownError(
        val message: String? = null
    ) : DomainError

    data class Exception(
        val throwable: Throwable
    ) : DomainError
}