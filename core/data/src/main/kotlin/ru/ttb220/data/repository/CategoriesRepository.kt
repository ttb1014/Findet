package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.Category

interface CategoriesRepository {
    fun getAllCategories(): Flow<List<Category>>

    fun getAllIncomeCategories(): Flow<List<Category>>

    fun getAllExpenseCategories(): Flow<List<Category>>
}