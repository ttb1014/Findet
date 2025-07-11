package ru.ttb220.network.api.exception

/**
 * В теории, никогда не выбросится
 */
class ForbiddenException(
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code = 403,
    message,
    responseBody
)