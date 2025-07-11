package ru.ttb220.app

import android.app.Application
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.app.di.AppComponent
import ru.ttb220.app.di.DaggerAppComponent
import ru.ttb220.categories.di.CategoriesComponent
import ru.ttb220.categories.di.CategoriesComponentProvider
import ru.ttb220.bottomsheet.di.BottomSheetComponent
import ru.ttb220.bottomsheet.di.BottomSheetComponentProvider
import ru.ttb220.expenses.di.ExpensesComponent
import ru.ttb220.expenses.di.ExpensesComponentProvider
import ru.ttb220.incomes.di.IncomesComponent
import ru.ttb220.incomes.di.IncomesComponentProvider

class FindetApplication : Application(),
    AccountComponentProvider,
    CategoriesComponentProvider,
    BottomSheetComponentProvider,
    ExpensesComponentProvider,
    IncomesComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun provideAccountComponent(): AccountComponent =
        appComponent.accountComponentFactory.create()

    override fun provideCategoriesComponent(): CategoriesComponent =
        appComponent.categoriesComponentFactory.create()

    override fun provideBottomSheetComponent(): BottomSheetComponent =
        appComponent.bottomSheetComponentFactory.create()

    override fun provideExpensesComponent(): ExpensesComponent =
        appComponent.expensesComponentFactory.create()

    override fun provideIncomesComponent(): IncomesComponent =
        appComponent.incomesComponentFactory.create()
}