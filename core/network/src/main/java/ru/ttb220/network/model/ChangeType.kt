package ru.ttb220.network.model

import kotlinx.serialization.Serializable

@Serializable
enum class ChangeType {
    CREATION,
    MODIFICATION
}
