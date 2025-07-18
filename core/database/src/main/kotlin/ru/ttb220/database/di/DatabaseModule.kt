package ru.ttb220.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ttb220.database.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): AppDatabase {
        val db = Room
            .databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = AppDatabase.DATABASE_NAME
            )
            .build()

        return db
    }

    @Singleton
    @Provides
    fun providesAccountsDao(
        db: AppDatabase
    ) = db.accountsDao()

    @Singleton
    @Provides
    fun providesTransactionsDao(
        db: AppDatabase
    ) = db.transactionsDao()

    @Singleton
    @Provides
    fun providesCategoriesDao(
        db: AppDatabase
    ) = db.categoriesDao()
}