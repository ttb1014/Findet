package ru.ttb220.findet.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import kotlinx.datetime.TimeZone
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.bottomsheet.di.BottomSheetComponent
import ru.ttb220.category.di.CategoriesComponent
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.sync.SyncManager
import ru.ttb220.expense.di.ExpensesComponent
import ru.ttb220.income.di.IncomesComponent
import ru.ttb220.setting.di.SettingsComponent
import ru.ttb220.sync.SyncWorker
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
    ],
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(context: Context)

    val viewModelFactory: ViewModelProvider.Factory
    val accountComponentFactory: AccountComponent.Factory
    val categoriesComponentFactory: CategoriesComponent.Factory
    val bottomSheetComponentFactory: BottomSheetComponent.Factory
    val expensesComponentFactory: ExpensesComponent.Factory
    val incomesComponentFactory: IncomesComponent.Factory
    val settingsComponentFactory: SettingsComponent.Factory

    val assistedFactory: SyncWorker.AssistedFactory

    val networkMonitor: NetworkMonitor

    val syncManager: SyncManager
    val timeZone: TimeZone
    val settingsRepository: SettingsRepository
}