package ru.ttb220.network.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.network.RetrofitNetworkInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestModule::class
    ]

)
interface TestAppComponent {
    fun inject(test: RetrofitNetworkInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}