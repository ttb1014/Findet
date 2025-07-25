package ru.ttb220.expense.presentation.model

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.util.CurrencyMapper

data class ExpenseScreenData(
    val expenseId: Int? = null,
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

fun ExpenseScreenData.toTransactionBrief() = TransactionBrief(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.filterNot { it == ' ' }.toDouble(),
    transactionDate = Instant.fromEpochMilliseconds(dateMillis),
    comment = comment
)

fun TransactionDetailed.toExpenseScreenData(timeZone: TimeZone) = ExpenseScreenData(
    expenseId = id,
    accountId = account.id,
    accountName = account.name,
    categoryId = category.id,
    categoryName = category.name,
    amount = amount.toString(),
    currencySymbol = CurrencyMapper.getSymbol(account.currency),
    date = transactionDate.toLocalDateTime(timeZone).date.toString(),
    dateMillis = transactionDate.toEpochMilliseconds(),
    time = "${transactionDate.toLocalDateTime(timeZone).hour}:${
        transactionDate.toLocalDateTime(
            timeZone
        ).minute
    }",
    isDatePickerShown = false,
    comment = comment
)