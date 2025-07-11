package ru.ttb220.incomes.presentation.model

import kotlinx.datetime.Instant
import ru.ttb220.model.transaction.TransactionBrief

data class AddIncomeScreenData(
    val accountId: Int,
    val accountName: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: String,
    val currencySymbol: String,
    val date: String,
    val dateMillis: Long,
    val time: String,
    val isDatePickerShown: Boolean = false,
    val comment: String? = null,
)

fun AddIncomeScreenData.toTransactionBrief() = TransactionBrief(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.filter { it.isDigit() },
    transactionDate = Instant.fromEpochMilliseconds(dateMillis),
    comment = comment
)