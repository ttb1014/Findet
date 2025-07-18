package ru.ttb220.findet.ui

import ru.ttb220.account.presentation.navigation.ACCOUNT_SCREEN_ROUTE_BASE
import ru.ttb220.account.presentation.navigation.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.designsystem.theme.Green
import ru.ttb220.designsystem.theme.LightSurface
import ru.ttb220.expense.presentation.navigation.ADD_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.ANALYSE_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EDIT_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.ADD_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.ANALYSE_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.EDIT_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.presentation.model.R

// maps screen route to its visual
val RouteToTabTextMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to R.string.expenses_today,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to R.string.incomes_today,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.string.my_history,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.string.my_history,
    ACCOUNT_SCREEN_ROUTE_BASE to R.string.my_account,
    ADD_ACCOUNT_SCREEN_ROUTE to R.string.new_account,
    ADD_INCOME_SCREEN_ROUTE_BASE to R.string.new_income,
    ADD_EXPENSE_SCREEN_ROUTE_BASE to R.string.new_expense,
    EDIT_EXPENSE_SCREEN_ROUTE_BASE to R.string.edit_expense,
    EDIT_INCOME_SCREEN_ROUTE_BASE to R.string.edit_income,
    ANALYSE_EXPENSE_SCREEN_ROUTE_BASE to R.string.analysis,
    ANALYSE_INCOME_SCREEN_ROUTE_BASE to R.string.analysis,
    "categories" to R.string.my_categories,
    "settings" to R.string.settings,
)

// maps screen route to its visual
val RouteToTabLeadingIconMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to null,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to null,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.back,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.back,
    ACCOUNT_SCREEN_ROUTE_BASE to null,
    ADD_ACCOUNT_SCREEN_ROUTE to R.drawable.cross,
    ADD_INCOME_SCREEN_ROUTE_BASE to R.drawable.cross,
    ADD_EXPENSE_SCREEN_ROUTE_BASE to R.drawable.cross,
    EDIT_EXPENSE_SCREEN_ROUTE_BASE to R.drawable.cross,
    EDIT_INCOME_SCREEN_ROUTE_BASE to R.drawable.cross,
    ANALYSE_EXPENSE_SCREEN_ROUTE_BASE to R.drawable.back,
    ANALYSE_INCOME_SCREEN_ROUTE_BASE to R.drawable.back,
    "categories" to null,
    "settings" to null,
)

// maps screen route to its visual
val RouteToTabTrailingIconMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to R.drawable.history,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to R.drawable.history,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.clipboard_text,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.clipboard_text,
    ACCOUNT_SCREEN_ROUTE_BASE to R.drawable.edit,
    ADD_ACCOUNT_SCREEN_ROUTE to R.drawable.check,
    ADD_INCOME_SCREEN_ROUTE_BASE to R.drawable.check,
    ADD_EXPENSE_SCREEN_ROUTE_BASE to R.drawable.check,
    EDIT_EXPENSE_SCREEN_ROUTE_BASE to R.drawable.check,
    EDIT_INCOME_SCREEN_ROUTE_BASE to R.drawable.check,
    ANALYSE_EXPENSE_SCREEN_ROUTE_BASE to null,
    ANALYSE_INCOME_SCREEN_ROUTE_BASE to null,
    "categories" to null,
    "settings" to null,
)

// maps screen route to its visuals
val RouteToTabDataMapper = RouteToTabTextMapper.mapValues { (route, _) ->
    TopAppBarData(
        RouteToTabTextMapper[route]!!,
        RouteToTabLeadingIconMapper[route],
        RouteToTabTrailingIconMapper[route],
        if (route == ANALYSE_EXPENSE_SCREEN_ROUTE_BASE || route == ANALYSE_INCOME_SCREEN_ROUTE_BASE) LightSurface else Green
    )
}
