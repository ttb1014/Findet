package ru.ttb220.findet.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.bottomsheet.di.BottomSheetComponent
import ru.ttb220.bottomsheet.di.BottomSheetModule
import ru.ttb220.category.di.CategoriesComponent
import ru.ttb220.common.di.DaggerVMFactory
import ru.ttb220.common.di.ViewModelKey
import ru.ttb220.data.di.DataModule
import ru.ttb220.expense.di.ExpensesComponent
import ru.ttb220.expense.di.ExpensesModule
import ru.ttb220.findet.ui.viewmodel.MainViewModel
import ru.ttb220.income.di.IncomesComponent
import ru.ttb220.income.di.IncomesModule
import ru.ttb220.pin.di.PinComponent
import ru.ttb220.setting.di.SettingsComponent
import ru.ttb220.setting.di.SettingsModule
import ru.ttb220.sync.di.SyncModule

@Module(
    includes = [
        DataModule::class,
        BottomSheetModule::class,
        IncomesModule::class,
        ExpensesModule::class,
        SyncModule::class,
    ],
    subcomponents = [
        AccountComponent::class,
        CategoriesComponent::class,
        BottomSheetComponent::class,
        ExpensesComponent::class,
        IncomesComponent::class,
        SettingsComponent::class,
        PinComponent::class
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