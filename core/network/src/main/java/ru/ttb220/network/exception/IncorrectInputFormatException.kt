package ru.ttb220.network.exception

class IncorrectInputFormatException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code,
    message,
    responseBody
)