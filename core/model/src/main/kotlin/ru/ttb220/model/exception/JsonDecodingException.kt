package ru.ttb220.model.exception

import java.io.IOException

class JsonDecodingException(
    message: String? = null
) : IOException(message)