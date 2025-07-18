package ru.ttb220.expense.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.expense.presentation.viewmodel.AddExpenseViewModel
import ru.ttb220.expense.presentation.viewmodel.ExpensesHistoryViewModel
import ru.ttb220.expense.presentation.viewmodel.ExpensesTodayViewModel

@Module(
    includes = []
)
interface ExpensesModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesHistoryViewModel::class)
    fun bindExpensesHistoryViewModel(viewModel: ExpensesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesTodayViewModel::class)
    fun bindExpensesTodayViewModel(viewModel: ExpensesTodayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddExpenseViewModel::class)
    fun bindAddExpenseViewModel(viewModel: AddExpenseViewModel): ViewModel
}