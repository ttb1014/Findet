package ru.ttb220.pin.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.pin.presentation.viewmodel.PinViewModel

@Module()
interface PinModule {

    @Binds
    @IntoMap
    @ViewModelKey(PinViewModel::class)
    fun bindIncomesHistoryViewModel(viewModel: PinViewModel): ViewModel
}