package ru.ttb220.currencyselector.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.currencyselector.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.data.di.DataModule
import ru.ttb220.presentation.ui.util.ViewModelKey

@Module(
    includes = [
        DataModule::class
    ]
)
interface CurrencySelectorModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    fun bindCategoriesViewModel(viewModel: CurrencyViewModel): ViewModel
}