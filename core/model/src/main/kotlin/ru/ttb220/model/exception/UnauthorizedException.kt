package ru.ttb220.model.exception

class UnauthorizedException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 401,
    message,
    responseBody
)