package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.domain_model.Category

interface CategoriesRepository {
    fun getAllCategories(): Flow<Result<List<Category>>>

    fun getAllIncomeCategories(): Flow<Result<List<Category>>>

    fun getAllExpenseCategories(): Flow<Result<List<Category>>>
}