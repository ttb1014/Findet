package ru.ttb220.presentation.model.util

import androidx.annotation.StringRes
import ru.ttb220.model.DomainError
import ru.ttb220.presentation.model.R

object DomainErrorMessageMapper {

    @StringRes
    fun toMessageRes(error: DomainError): Int = when (error) {
        DomainError.NoInternet     -> R.string.error_no_internet
        DomainError.NotFound       -> R.string.error_not_found
        DomainError.InvalidInput   -> R.string.error_invalid_input
        is DomainError.UnknownError -> R.string.error_unknown
        DomainError.ApiError -> R.string.error_api
        is DomainError.Exception -> R.string.error_unknown
    }
}