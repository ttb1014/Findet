package ru.ttb220.data.api.legacy

import kotlinx.coroutines.flow.Flow
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult

/**
 * Each method provides either wrapped data or wrapped domain error. Emits single value.
 */
@Deprecated("Use Syncable instead")
interface CategoriesRepository {
    fun getAllCategories(): Flow<SafeResult<List<Category>>>

    fun getAllIncomeCategories(): Flow<SafeResult<List<Category>>>

    fun getAllExpenseCategories(): Flow<SafeResult<List<Category>>>
}