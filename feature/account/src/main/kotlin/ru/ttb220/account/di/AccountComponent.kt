package ru.ttb220.account.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(
    modules = [
        AccountModule::class,
    ]
)
@Singleton
interface AccountComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AccountComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}