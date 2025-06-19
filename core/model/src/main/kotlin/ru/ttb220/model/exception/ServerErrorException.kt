package ru.ttb220.model.exception

class ServerErrorException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ApiException(
    code,
    message,
    responseBody
)