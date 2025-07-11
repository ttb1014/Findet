package ru.ttb220.account.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel
import ru.ttb220.common.di.ViewModelKey

@Module(
    includes = [
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