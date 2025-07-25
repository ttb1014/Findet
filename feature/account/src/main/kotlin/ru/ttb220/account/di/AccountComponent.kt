package ru.ttb220.account.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent(
    modules = [
        AccountModule::class,
    ]
)
interface AccountComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AccountComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}