package ru.ttb220.category.domain

import kotlinx.coroutines.flow.Flow
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    operator fun invoke(): Flow<SafeResult<List<Category>>> {
        return categoriesRepository.getAllCategories()
    }
}