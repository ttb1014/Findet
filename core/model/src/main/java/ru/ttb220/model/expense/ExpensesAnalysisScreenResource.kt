package ru.ttb220.model.expense

class ExpensesAnalysisScreenResource(
    val startDate:String,
    val endDate:String,
    val amount:String,
    val expenses:List<ExpenseAnalysisResource>
)