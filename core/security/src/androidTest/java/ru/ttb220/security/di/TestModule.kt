package ru.ttb220.security.di

import dagger.Module

@Module(
    includes = [
        SecurityModule::class,
    ]
)
class TestModule
