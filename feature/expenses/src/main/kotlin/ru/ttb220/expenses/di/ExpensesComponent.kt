package ru.ttb220.expenses.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent()
interface ExpensesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExpensesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}