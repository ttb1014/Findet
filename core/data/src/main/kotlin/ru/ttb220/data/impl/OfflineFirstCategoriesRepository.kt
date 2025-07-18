package ru.ttb220.data.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.database.dao.CategoriesDao
import ru.ttb220.database.entity.CategoryEntity
import ru.ttb220.database.mapper.toCategory
import ru.ttb220.database.mapper.toCategoryEntity
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.mapper.toCategory
import javax.inject.Inject

class OfflineFirstCategoriesRepository @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val remoteDataSource: RemoteDataSource
) : CategoriesRepository, Syncable {

    override fun getAllCategories(): Flow<SafeResult<List<Category>>> =
        categoriesDao.getAllCategories()
            .toSafeDomainResult()

    override fun getAllIncomeCategories(): Flow<SafeResult<List<Category>>> =
        categoriesDao.getCategoriesByType(true)
            .toSafeDomainResult()


    override fun getAllExpenseCategories(): Flow<SafeResult<List<Category>>> =
        categoriesDao.getCategoriesByType(false)
            .toSafeDomainResult()

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        remoteDataSource.getAllCategories().forEach { categoryDto ->
            val category = categoryDto.toCategory()

            launch {
                val categoryEntity = categoriesDao.getCategoryById(category.id.toLong())

                if (categoryEntity == null) {
                    categoriesDao.insertCategory(category.toCategoryEntity())
                    return@launch
                }

                if (categoryEntity.toCategory() != category) {
                    categoriesDao.updateCategory(category.toCategoryEntity())
                }
            }
        }

        true
    }

    private fun Flow<List<CategoryEntity>>.toSafeDomainResult() = map { categories ->
        SafeResult.Success(categories.map {
            it.toCategory()
        }
        )
    }

    companion object {
        private const val TAG = "OfflineFirstCategoriesRepository"
    }
}