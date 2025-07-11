package ru.ttb220.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.app.ui.viewmodel.MainViewModel
import ru.ttb220.bottomsheet.di.BottomSheetComponent
import ru.ttb220.bottomsheet.di.BottomSheetModule
import ru.ttb220.categories.di.CategoriesComponent
import ru.ttb220.common.di.DaggerVMFactory
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.data.di.DataModule
import ru.ttb220.expenses.di.ExpensesComponent
import ru.ttb220.expenses.di.ExpensesModule
import ru.ttb220.incomes.di.IncomesComponent
import ru.ttb220.incomes.di.IncomesModule

@Module(
    includes = [
        DataModule::class,
        BottomSheetModule::class,
        IncomesModule::class,
        ExpensesModule::class
    ],
    subcomponents = [
        AccountComponent::class,
        CategoriesComponent::class,
        BottomSheetComponent::class,
        ExpensesComponent::class,
        IncomesComponent::class,
    ]
)
interface AppModule {

    @Binds
    fun bindsViewModelFactory(daggerVMFactory: DaggerVMFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
}