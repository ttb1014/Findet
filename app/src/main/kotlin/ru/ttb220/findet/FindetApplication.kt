package ru.ttb220.findet

import android.app.Application
import ru.ttb220.account.di.AccountComponent
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.bottomsheet.di.BottomSheetComponent
import ru.ttb220.bottomsheet.di.BottomSheetComponentProvider
import ru.ttb220.category.di.CategoriesComponent
import ru.ttb220.category.di.CategoriesComponentProvider
import ru.ttb220.expense.di.ExpensesComponent
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.findet.di.AppComponent
import ru.ttb220.findet.di.DaggerAppComponent
import ru.ttb220.income.di.IncomesComponent
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.pin.di.PinComponent
import ru.ttb220.pin.di.PinComponentProvider
import ru.ttb220.setting.di.SettingsComponent
import ru.ttb220.setting.di.SettingsComponentProvider
import ru.ttb220.sync.SyncWorkerFactory

class FindetApplication :
    AccountComponentProvider,
    CategoriesComponentProvider,
    BottomSheetComponentProvider,
    ExpensesComponentProvider,
    IncomesComponentProvider,
    SettingsComponentProvider,
    PinComponentProvider,

    Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        SyncWorkerFactory.assistedFactory = appComponent.assistedFactory
        appComponent.syncManager.init()
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

    override fun provideSettingsComponent(): SettingsComponent =
        appComponent.settingsComponentFactory.create()

    override fun providePinComponent(): PinComponent =
        appComponent.pinComponentFactory.create()
}