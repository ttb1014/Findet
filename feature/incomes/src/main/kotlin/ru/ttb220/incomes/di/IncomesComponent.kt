package ru.ttb220.incomes.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent()
interface IncomesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): IncomesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}