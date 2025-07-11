package ru.ttb220.network.api.exception

import androidx.annotation.IntRange

open class ClientErrorException(
    @IntRange(from = 400, to = 499)
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ApiException(
    code,
    message,
    responseBody
)