package ru.ttb220.categories.di

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(
    modules = [
        CategoriesModule::class]
)
@Singleton
interface CategoriesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoriesComponent
    }

    val viewModelFactory: ViewModelProvider.Factory
}