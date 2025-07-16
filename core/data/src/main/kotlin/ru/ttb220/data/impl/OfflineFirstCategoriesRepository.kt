package ru.ttb220.data.impl

import kotlinx.coroutines.flow.Flow
import ru.ttb220.data.api.SyncableCategoriesRepository
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import javax.inject.Inject

class OfflineFirstCategoriesRepository @Inject constructor(

) : SyncableCategoriesRepository {

    override fun getAllCategories(): Flow<SafeResult<List<Category>>> {
        TODO("Not yet implemented")
    }

    override fun getAllIncomeCategories(): Flow<SafeResult<List<Category>>> {
        TODO("Not yet implemented")
    }

    override fun getAllExpenseCategories(): Flow<SafeResult<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }
}