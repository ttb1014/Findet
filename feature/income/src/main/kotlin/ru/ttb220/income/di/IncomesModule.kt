package ru.ttb220.income.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.income.presentation.viewmodel.IncomesHistoryViewModel
import ru.ttb220.income.presentation.viewmodel.IncomesTodayViewModel
import ru.ttb220.income.presentation.viewmodel.AddIncomeViewModel
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.income.presentation.viewmodel.IncomesAnalysisScreenViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(IncomesAnalysisScreenViewModel::class)
    fun findIncomesAnalysisScreenViewModel(viewModel:IncomesAnalysisScreenViewModel): ViewModel
}