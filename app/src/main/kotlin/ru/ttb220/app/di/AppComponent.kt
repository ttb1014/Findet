package ru.ttb220.app.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.categories.di.CategoriesComponent
import ru.ttb220.currencyselector.di.CurrencySelectorComponent
import ru.ttb220.expenses.di.ExpensesComponent
import ru.ttb220.incomes.di.IncomesComponent
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
    val currencySelectorComponentFactory: CurrencySelectorComponent.Factory
    val expensesComponentFactory: ExpensesComponent.Factory
    val incomesComponentFactory: IncomesComponent.Factory
}