package ru.ttb220.setting.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

@Module()
interface SettingsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindIncomesHistoryViewModel(viewModel: SettingsViewModel): ViewModel
}