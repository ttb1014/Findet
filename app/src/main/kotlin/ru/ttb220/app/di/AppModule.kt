package ru.ttb220.app.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.app.MainViewModel
import ru.ttb220.categories.di.CategoriesComponent
import ru.ttb220.currencyselector.di.CurrencySelectorComponent
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.data.repository.internal.MockSettingsRepository
import ru.ttb220.expenses.di.ExpensesComponent
import ru.ttb220.incomes.di.IncomesComponent
import ru.ttb220.presentation.ui.util.DaggerVMFactory

@Module(
    subcomponents = [
        AccountComponent::class,
        CategoriesComponent::class,
        CurrencySelectorComponent::class,
        ExpensesComponent::class,
        IncomesComponent::class,
    ]
)
interface AppModule {

    @Binds
    fun bindsViewModelFactory(daggerVMFactory: DaggerVMFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
}

@Module
object AppModule2 {

    @Provides
    fun provideMainViewModel(settingsRepository: SettingsRepository): MainViewModel {
        return MainViewModel(settingsRepository)
    }
}