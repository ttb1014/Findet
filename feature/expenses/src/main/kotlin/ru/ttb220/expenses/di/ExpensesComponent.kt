package ru.ttb220.expenses.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(
    modules = [
        ExpensesModule::class,
    ]
)
interface ExpensesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExpensesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}