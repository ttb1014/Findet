package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.model.entity.CategoryEntity

@Dao
interface CategoriesDao {

    @Insert(onConflict = ABORT)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Update(onConflict = ABORT)
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT rowid, name, emoji, is_income FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT rowid, name, emoji, is_income FROM categories " +
            "WHERE is_income = :isIncome")
    fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryEntity>>
}