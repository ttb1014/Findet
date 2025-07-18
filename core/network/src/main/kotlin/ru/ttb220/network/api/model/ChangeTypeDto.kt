package ru.ttb220.network.api.model

import kotlinx.serialization.Serializable

@Serializable
enum class ChangeTypeDto {
    CREATION,
    MODIFICATION
}