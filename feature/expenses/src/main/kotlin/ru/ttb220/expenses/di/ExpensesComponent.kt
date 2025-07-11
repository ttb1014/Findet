package ru.ttb220.expenses.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import ru.ttb220.expenses.presentation.viewmodel.EditExpenseViewModel

@Subcomponent()
interface ExpensesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExpensesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory

    val assistedFactory: EditExpenseViewModel.Factory
}