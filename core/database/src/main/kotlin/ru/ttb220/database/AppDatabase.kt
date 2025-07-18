package ru.ttb220.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ttb220.database.converter.Converters
import ru.ttb220.database.dao.AccountsDao
import ru.ttb220.database.dao.CategoriesDao
import ru.ttb220.database.dao.TransactionsDao
import ru.ttb220.database.entity.AccountEntity
import ru.ttb220.database.entity.CategoryEntity
import ru.ttb220.database.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class
    ],
    exportSchema = true,
    version = 2,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun accountsDao(): AccountsDao

    companion object {
        const val DATABASE_NAME = "database.db"
    }
}