package ru.ttb220.currencyselector.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent(
    modules = [
//        CurrencySelectorModule::class,
    ]
)
interface CurrencySelectorComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CurrencySelectorComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}