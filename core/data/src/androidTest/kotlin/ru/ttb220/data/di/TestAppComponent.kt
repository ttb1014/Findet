package ru.ttb220.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ttb220.data.RepositoryInstrumentedTest
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
    ]
)
@Singleton
interface TestAppComponent {
    fun inject(test: RepositoryInstrumentedTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}