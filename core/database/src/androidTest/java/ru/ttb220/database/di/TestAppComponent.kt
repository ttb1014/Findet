package ru.ttb220.database.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.database.DatabaseInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestModule::class]

)
interface TestAppComponent {
    fun inject(test: DatabaseInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}