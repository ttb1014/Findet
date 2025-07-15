package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.model.entity.Category

@Dao
interface CategoriesDao {

    @Insert(onConflict = ABORT)
    suspend fun insertCategory(category: Category)

    @Update(onConflict = ABORT)
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT rowid, name, emoji, is_income FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT rowid, name, emoji, is_income FROM categories " +
            "WHERE is_income = :isIncome")
    fun getCategoriesByType(isIncome: Boolean): Flow<List<Category>>
}