package ru.ttb220.presentation_model.screen

import ru.ttb220.presentation_model.ExpenseAnalysisResource

class ExpensesAnalysisScreenResource(
    val startDate:String,
    val endDate:String,
    val amount:String,
    val expenses:List<ExpenseAnalysisResource>
)