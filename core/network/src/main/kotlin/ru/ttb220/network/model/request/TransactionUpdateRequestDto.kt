package ru.ttb220.network.model.request

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionUpdateRequestDto(
    @SerialName("accountId")
    val accountId: Int,

    @SerialName("categoryId")
    val categoryId: Int,

    @SerialName("amount")
    val amount: String,

    @SerialName("transactionDate")
    val transactionDate: Instant,

    @SerialName("comment")
    val comment: String? = null,
)