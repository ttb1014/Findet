package ru.ttb220.network.di

import android.app.Application
import ru.ttb220.network.di.DaggerTestAppComponent

class TestApplication : Application() {
    lateinit var testComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        testComponent = DaggerTestAppComponent.factory().create(this)
    }
}