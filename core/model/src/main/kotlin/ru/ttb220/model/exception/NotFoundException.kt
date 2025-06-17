package ru.ttb220.model.exception

class NotFoundException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 404,
    message,
    responseBody
)