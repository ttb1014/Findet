package ru.ttb220.currencyselector.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.currencyselector.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.common.di.ViewModelKey

@Module()
interface CurrencySelectorModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel
}