package ru.ttb220.categories.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.categories.presentation.viewmodel.CategoriesViewModel
import ru.ttb220.common.di.ViewModelKey

@Module(
    includes = [
    ]
)
interface CategoriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
}