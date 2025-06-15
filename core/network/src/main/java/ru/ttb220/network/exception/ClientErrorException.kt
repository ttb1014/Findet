package ru.ttb220.network.exception

open class ClientErrorException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ApiException(
    code,
    message,
    responseBody
)