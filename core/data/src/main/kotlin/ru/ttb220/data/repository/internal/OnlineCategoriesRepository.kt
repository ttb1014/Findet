package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.repository.CategoriesRepository
import ru.ttb220.data.util.withRetry
import ru.ttb220.data.util.wrapToSafeResult
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.model.toCategory
import javax.inject.Inject

internal class OnlineCategoriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CategoriesRepository {

    override fun getAllCategories(): Flow<SafeResult<List<Category>>> = flow<List<Category>> {
        emit(remoteDataSource.getAllCategories().map { it.toCategory() })
    }.withRetry()
        .wrapToSafeResult()

    override fun getAllIncomeCategories(): Flow<SafeResult<List<Category>>> = flow<List<Category>> {
        emit(remoteDataSource.getAllCategoriesByType(true).map { it.toCategory() })
    }.withRetry()
        .wrapToSafeResult()

    override fun getAllExpenseCategories(): Flow<SafeResult<List<Category>>> =
        flow<List<Category>> {
            emit(remoteDataSource.getAllCategoriesByType(false).map { it.toCategory() })
        }.withRetry()
            .wrapToSafeResult()
}