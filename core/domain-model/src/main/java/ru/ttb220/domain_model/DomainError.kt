package ru.ttb220.domain_model

sealed interface DomainError {
    data object NoInternet : DomainError
    data object Unauthorized : DomainError
    data object NotFound : DomainError
    data object InvalidInput : DomainError
    data object ServerError : DomainError
    data object Timeout : DomainError
    data class Unknown(val message: String? = null) : DomainError
}