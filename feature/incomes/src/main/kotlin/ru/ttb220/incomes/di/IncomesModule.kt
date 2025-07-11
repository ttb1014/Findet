package ru.ttb220.incomes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.incomes.presentation.viewmodel.IncomesHistoryViewModel
import ru.ttb220.incomes.presentation.viewmodel.IncomesTodayViewModel
import ru.ttb220.incomes.presentation.viewmodel.AddIncomeViewModel
import ru.ttb220.common.di.ViewModelKey

@Module()
interface IncomesModule {

    @Binds
    @IntoMap
    @ViewModelKey(IncomesHistoryViewModel::class)
    fun bindIncomesHistoryViewModel(viewModel: IncomesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesTodayViewModel::class)
    fun bindIncomesTodayViewModel(viewModel: IncomesTodayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddIncomeViewModel::class)
    fun bindAddIncomeViewModel(viewModel: AddIncomeViewModel): ViewModel
}