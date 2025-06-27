package ru.ttb220.network.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.network.model.AccountDto
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.toAccountState
import ru.ttb220.network.model.toCategory
import ru.ttb220.network.model.toTransactionStat

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

fun TransactionDetailedResponseDto.toTransactionDetailed(): TransactionDetailed =
    TransactionDetailed(
        id = id,
        account = account.toAccountState(),
        category = category.toCategory(),
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )