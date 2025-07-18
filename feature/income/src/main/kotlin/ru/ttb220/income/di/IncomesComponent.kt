package ru.ttb220.income.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import ru.ttb220.income.presentation.viewmodel.EditIncomeViewModel

@Subcomponent()
interface IncomesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): IncomesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory

    val assistedFactory: EditIncomeViewModel.Factory
}