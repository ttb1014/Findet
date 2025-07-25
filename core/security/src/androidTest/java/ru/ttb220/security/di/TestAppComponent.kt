package ru.ttb220.security.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.security.SecurityInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestModule::class
    ]

)
interface TestAppComponent {
    fun inject(test: SecurityInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}