package ru.ttb220.network.exception

import java.io.IOException

open class ApiException(
    val code: Int,
    message: String? = null,
    val responseBody: String? = null
) : IOException(message)