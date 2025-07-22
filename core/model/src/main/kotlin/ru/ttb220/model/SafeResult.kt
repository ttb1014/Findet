package ru.ttb220.model

sealed class SafeResult<out T> {

    data class Success<out T>(val data: T) : SafeResult<T>()

    data class Failure(val cause: DomainError) : SafeResult<Nothing>()
}
