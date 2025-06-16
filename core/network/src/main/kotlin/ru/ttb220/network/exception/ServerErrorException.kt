package ru.ttb220.network.exception

class ServerErrorException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ApiException(
    code,
    message,
    responseBody
)