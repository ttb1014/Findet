package ru.ttb220.database

import android.app.Application
import ru.ttb220.database.di.DaggerTestAppComponent
import ru.ttb220.database.di.TestAppComponent

class TestApplication : Application() {
    lateinit var testComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        testComponent = DaggerTestAppComponent.factory().create(this)
    }
}