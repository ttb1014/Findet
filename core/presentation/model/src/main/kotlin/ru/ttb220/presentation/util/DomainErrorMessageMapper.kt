package ru.ttb220.presentation.util

import androidx.annotation.StringRes
import ru.ttb220.model.DomainError
import ru.ttb220.presentation.model.R

object DomainErrorMessageMapper {

    @StringRes
    fun toMessageRes(error: DomainError): Int = when (error) {
        DomainError.NoInternet     -> R.string.error_no_internet
        DomainError.Unauthorized   -> R.string.error_unauthorized
        DomainError.NotFound       -> R.string.error_not_found
        DomainError.InvalidInput   -> R.string.error_invalid_input
        DomainError.ServerError    -> R.string.error_server_error
        DomainError.Timeout        -> R.string.error_timeout
        is DomainError.Unknown     -> R.string.error_unknown
    }
}