package ru.ttb220.network.exception

class UnauthorizedException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 401,
    message,
    responseBody
)