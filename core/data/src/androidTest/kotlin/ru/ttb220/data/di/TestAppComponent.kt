package ru.ttb220.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.data.DataInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestModule::class
    ]

)
interface TestAppComponent {
    fun inject(test: DataInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}