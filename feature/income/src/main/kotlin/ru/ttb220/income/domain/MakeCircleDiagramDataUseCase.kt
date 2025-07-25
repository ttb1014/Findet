package ru.ttb220.income.domain

import ru.ttb220.chart.api.model.CircleDiagramData
import ru.ttb220.income.presentation.model.AvailableDiagramColors
import ru.ttb220.model.Category
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class MakeCircleDiagramDataUseCase @Inject constructor() {

    operator fun invoke(
        transactions: List<TransactionDetailed>,
        categories: List<Category>,
    ): CircleDiagramData {
        val totalAmount = transactions.sumOf { it.amount }

        if (totalAmount == 0.0) return CircleDiagramData(emptyList())

        val availableColors = AvailableDiagramColors.entries
        var colorIndex = 0

        val data = categories.mapNotNull { category ->
            val categoryTransactions = transactions.filter { it.category.id == category.id }
            val categorySum = categoryTransactions.sumOf { it.amount }
            if (categorySum == 0.0)
                return@mapNotNull null

            val percentage = ((categorySum / totalAmount) * 100).toInt()
            val color = availableColors[colorIndex % availableColors.size].color
            colorIndex++

            Triple(category.name, percentage, color)
        }

        return CircleDiagramData(data)
    }
}