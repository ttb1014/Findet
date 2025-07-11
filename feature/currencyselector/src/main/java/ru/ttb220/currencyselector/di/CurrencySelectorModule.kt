package ru.ttb220.currencyselector.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.currencyselector.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.currencyselector.presentation.viewmodel.AccountBottomSheetViewModel
import ru.ttb220.currencyselector.presentation.viewmodel.CategoryBottomSheetViewModel

@Module()
interface CurrencySelectorModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountBottomSheetViewModel::class)
    fun bindAccountBottomSheetViewModel(viewModel: AccountBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryBottomSheetViewModel::class)
    fun bindCategoryBottomSheetViewModel(viewModel: CategoryBottomSheetViewModel): ViewModel
}