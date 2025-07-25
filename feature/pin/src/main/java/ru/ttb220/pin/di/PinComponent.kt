package ru.ttb220.pin.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent(
    modules = [
        PinModule::class
    ]
)
interface PinComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PinComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}