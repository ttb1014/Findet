package ru.ttb220.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String? = null
)