package ru.ttb220.data.di

import android.app.Application
import ru.ttb220.data.di.DaggerTestAppComponent

class TestApplication : Application() {
    lateinit var testComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        testComponent = DaggerTestAppComponent.factory().create(this)
    }
}