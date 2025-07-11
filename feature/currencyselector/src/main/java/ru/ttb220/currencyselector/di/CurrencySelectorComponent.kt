package ru.ttb220.currencyselector.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(
    modules = [
    CurrencySelectorModule::class,
    ]
)
@Singleton
interface CurrencySelectorComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CurrencySelectorComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}