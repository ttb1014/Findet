package ru.ttb220.data

import android.app.Application
import ru.ttb220.data.di.DaggerTestAppComponent
import ru.ttb220.data.di.TestAppComponent

class TestApplication : Application() {
    lateinit var testComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        testComponent = DaggerTestAppComponent.factory().create(this)
    }
}