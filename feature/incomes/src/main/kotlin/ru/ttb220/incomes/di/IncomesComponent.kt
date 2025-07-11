package ru.ttb220.incomes.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import ru.ttb220.incomes.presentation.viewmodel.EditIncomeViewModel
import javax.inject.Singleton

@Subcomponent()
interface IncomesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): IncomesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory

    val assistedFactory: EditIncomeViewModel.Factory
}