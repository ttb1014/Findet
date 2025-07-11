package ru.ttb220.network.api.exception

import androidx.annotation.IntRange
import java.io.IOException

open class ApiException(
    @IntRange(from = 400, to = 599)
    val code: Int,
    message: String? = null,
    val responseBody: String? = null
) : IOException(message)