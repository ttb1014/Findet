package ru.ttb220.model.exception

open class ClientErrorException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ApiException(
    code,
    message,
    responseBody
)