package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.repository.CategoriesRepository
import ru.ttb220.data.util.withRetry
import ru.ttb220.data.util.wrapToResult
import ru.ttb220.model.Category
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.model.CategoryDto
import javax.inject.Inject

internal class OnlineCategoriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CategoriesRepository {

    override fun getAllCategories(): Flow<Result<List<Category>>> = flow<List<Category>> {
        emit(remoteDataSource.getAllCategories().map { it.toCategory() })
    }.withRetry()
        .wrapToResult()

    override fun getAllIncomeCategories(): Flow<Result<List<Category>>> = flow<List<Category>> {
        emit(remoteDataSource.getAllCategoriesByType(true).map { it.toCategory() })
    }.withRetry()
        .wrapToResult()

    override fun getAllExpenseCategories(): Flow<Result<List<Category>>> = flow<List<Category>> {
        emit(remoteDataSource.getAllCategoriesByType(false).map { it.toCategory() })
    }.withRetry()
        .wrapToResult()
}

private fun CategoryDto.toCategory() = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)