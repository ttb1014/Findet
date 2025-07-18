package ru.ttb220.category.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CategoriesModule::class]
)
interface CategoriesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoriesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}