package ru.ttb220.network.api.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.network.api.model.AccountDto
import ru.ttb220.network.api.model.CategoryDto

@Serializable
data class TransactionDetailedResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("account")
    val account: AccountDto,

    @SerialName("category")
    val category: CategoryDto,

    @SerialName("amount")
    val amount: String,

    @SerialName("transactionDate")
    val transactionDate: Instant,

    @SerialName("comment")
    val comment: String?,

    @SerialName("createdAt")
    val createdAt: Instant,

    @SerialName("updatedAt")
    val updatedAt: Instant,
)