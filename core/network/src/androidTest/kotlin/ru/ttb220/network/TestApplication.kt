package ru.ttb220.network

import android.app.Application
import ru.ttb220.network.di.DaggerTestAppComponent
import ru.ttb220.network.di.TestAppComponent

class TestApplication : Application() {
    lateinit var testComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        testComponent = DaggerTestAppComponent.factory().create(this)
    }
}