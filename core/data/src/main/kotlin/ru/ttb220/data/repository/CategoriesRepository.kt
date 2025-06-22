package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult

interface CategoriesRepository {
    fun getAllCategories(): Flow<SafeResult<List<Category>>>

    fun getAllIncomeCategories(): Flow<SafeResult<List<Category>>>

    fun getAllExpenseCategories(): Flow<SafeResult<List<Category>>>
}