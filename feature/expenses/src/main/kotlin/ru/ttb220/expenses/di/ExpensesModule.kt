package ru.ttb220.expenses.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.expenses.presentation.viewmodel.ExpensesHistoryViewModel
import ru.ttb220.expenses.presentation.viewmodel.ExpensesTodayViewModel
import ru.ttb220.common.di.ViewModelKey

@Module(
    includes = [
    ]
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
}