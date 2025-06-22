package ru.ttb220.network.exception

import java.io.IOException

class JsonDecodingException(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(
    message, cause
)