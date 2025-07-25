package ru.ttb220.datastore.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.datastore.DataStoreInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestModule::class
    ]

)
interface TestAppComponent {
    fun inject(test: DataStoreInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}