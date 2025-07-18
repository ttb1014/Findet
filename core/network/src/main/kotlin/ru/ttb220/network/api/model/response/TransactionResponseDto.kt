package ru.ttb220.network.api.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("accountId")
    val accountId: Int,

    @SerialName("categoryId")
    val categoryId: Int,

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


