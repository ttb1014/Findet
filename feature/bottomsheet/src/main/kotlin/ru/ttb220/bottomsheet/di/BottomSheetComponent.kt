package ru.ttb220.bottomsheet.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent()
interface BottomSheetComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): BottomSheetComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}