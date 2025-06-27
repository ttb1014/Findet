package ru.ttb220.network.model

import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountChangeType

@Serializable
enum class ChangeTypeDto {
    CREATION,
    MODIFICATION
}

fun ChangeTypeDto.toAccountChangeType(): AccountChangeType = when (this) {
    ChangeTypeDto.CREATION -> AccountChangeType.CREATION
    ChangeTypeDto.MODIFICATION -> AccountChangeType.MODIFICATION
}