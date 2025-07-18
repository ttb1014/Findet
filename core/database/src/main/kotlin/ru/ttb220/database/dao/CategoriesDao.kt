package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.entity.CategoryEntity

@Dao
interface CategoriesDao {

    @Insert(onConflict = ABORT)
    suspend fun insertCategory(categoryEntity: CategoryEntity): Long

    @Update(onConflict = ABORT)
    suspend fun updateCategory(categoryEntity: CategoryEntity): Int

    @Upsert
    suspend fun upsertCategory(categoryEntity: CategoryEntity): Long

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity): Int

    @Insert(onConflict = ABORT)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Update(onConflict = ABORT)
    suspend fun updateCategories(categories: List<CategoryEntity>)

    @Upsert
    suspend fun upsertCategories(categories: List<CategoryEntity>)

    @Delete
    suspend fun deleteCategories(categories: List<CategoryEntity>)

    @Query(
        "SELECT rowid, name, emoji, is_income FROM categories " +
                "WHERE rowid = :id " +
                "LIMIT 1"
    )
    suspend fun getCategoryById(id: Long): CategoryEntity?


    @Query("SELECT rowid, name, emoji, is_income FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query(
        "SELECT rowid, name, emoji, is_income FROM categories " +
                "WHERE is_income = :isIncome"
    )
    fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryEntity>>
}