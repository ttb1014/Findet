package ru.ttb220.app.navigation

import ru.ttb220.account.add.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.account.main.ACCOUNT_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.history.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.today.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.history.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.today.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.presentation.model.R

val RouteToTabTextMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to R.string.expenses_today,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to R.string.incomes_today,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.string.my_history,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.string.my_history,
    ACCOUNT_SCREEN_ROUTE_BASE to R.string.my_account,
    ADD_ACCOUNT_SCREEN_ROUTE to R.string.new_account,
    "categories" to R.string.my_categories,
    "settings" to R.string.settings,
)

val RouteToTabLeadingIconMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to null,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to null,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.back,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.back,
    ACCOUNT_SCREEN_ROUTE_BASE to null,
    ADD_ACCOUNT_SCREEN_ROUTE to R.drawable.cross,
    "categories" to null,
    "settings" to null,
)

val RouteToTabTrailingIconMapper = mapOf(
    EXPENSES_TODAY_SCREEN_ROUTE_BASE to R.drawable.history,
    INCOMES_TODAY_SCREEN_ROUTE_BASE to R.drawable.history,
    EXPENSES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.clipboard_text,
    INCOMES_HISTORY_SCREEN_ROUTE_BASE to R.drawable.clipboard_text,
    ACCOUNT_SCREEN_ROUTE_BASE to R.drawable.edit,
    ADD_ACCOUNT_SCREEN_ROUTE to R.drawable.check,
    "categories" to null,
    "settings" to null,
)

val RouteToTabDataMapper = RouteToTabTextMapper.mapValues { (route, _) ->
    TopAppBarData(
        RouteToTabTextMapper[route]!!,
        RouteToTabLeadingIconMapper[route],
        RouteToTabTrailingIconMapper[route],
    )
}
