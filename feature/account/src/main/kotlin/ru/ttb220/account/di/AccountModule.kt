package ru.ttb220.account.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel
import ru.ttb220.data.di.DataModule
import ru.ttb220.presentation.ui.util.ViewModelKey

@Module(
    includes = [
        DataModule::class
    ]
)
interface AccountModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAccountViewModel::class)
    fun bindAddAccountViewModel(viewModel: AddAccountViewModel): ViewModel
}