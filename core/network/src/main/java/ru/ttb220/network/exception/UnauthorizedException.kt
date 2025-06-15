package ru.ttb220.network.exception

class UnauthorizedException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code,
    message,
    responseBody
)