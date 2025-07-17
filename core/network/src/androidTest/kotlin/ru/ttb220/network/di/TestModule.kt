package ru.ttb220.network.di

import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
    ]
)
class TestModule
