package ru.ttb220.network.exception

/**
 * В теории, никогда не выбросится
 */
class ForbiddenException(
    code: Int,
    message: String? = null,
    responseBody: String? = null
) : ClientErrorException(
    code,
    message,
    responseBody
)