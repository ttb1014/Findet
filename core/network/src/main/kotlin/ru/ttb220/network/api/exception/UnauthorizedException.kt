package ru.ttb220.network.api.exception

class UnauthorizedException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 401,
    message,
    responseBody
)