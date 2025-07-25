package ru.ttb220.account.domain

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.account.presentation.model.defaultBarColorResolver
import ru.ttb220.chart.api.model.BarChartData
import ru.ttb220.model.transaction.Transaction
import javax.inject.Inject

class MakeBarChartDataUseCase @Inject constructor(
    private val timeZone: TimeZone,
) {

    operator fun invoke(
        transactions: List<Transaction>,
        startPeriod: LocalDate,
        endPeriod: LocalDate,
        isAxisShown: Boolean,
    ): BarChartData {
        // Собираем список всех дат в периоде
        val daysInPeriod = generateSequence(startPeriod) { date ->
            if (date < endPeriod) date.plus(1, DateTimeUnit.DAY) else null
        }.toList()

        // Группируем транзакции по дате
        val amountsByDay =
            transactions.groupBy { it.transactionDate.toLocalDateTime(timeZone).date }
                .mapValues { (date, trans) ->
                    trans.sumOf { it.amount }
                }

        // Значения для графика (если нет транзакций в день — 0f)
        val barValues = daysInPeriod.map { date ->
            amountsByDay[date]?.toFloat() ?: 0f
        }

        // Подписи: первая, середина и последняя дата
        val labels = listOf(
            daysInPeriod.firstOrNull()?.let {
                "${it.dayOfMonth}.${it.monthNumber}"
            } ?: "",
            daysInPeriod.getOrNull(daysInPeriod.size / 2)
                ?.let {
                    "${it.dayOfMonth}.${it.monthNumber}"
                } ?: "",
            daysInPeriod.lastOrNull()?.let {
                "${it.dayOfMonth}.${it.monthNumber}"
            } ?: "",
        )

        return BarChartData(
            isAxisShown = isAxisShown,
            barValues = barValues,
            labels = labels,
            barColorResolver = defaultBarColorResolver
        )
    }
}