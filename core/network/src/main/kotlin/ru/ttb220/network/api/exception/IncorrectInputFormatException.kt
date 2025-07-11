package ru.ttb220.network.api.exception

class IncorrectInputFormatException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 400,
    message,
    responseBody
)