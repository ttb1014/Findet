package ru.ttb220.bottomsheet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.bottomsheet.presentation.model.CategoryBottomSheetState
import ru.ttb220.bottomsheet.presentation.model.CategoryBottomSheetType
import ru.ttb220.bottomsheet.presentation.model.toCategoryData
import ru.ttb220.data.api.legacy.CategoriesRepository
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import javax.inject.Inject

class CategoryBottomSheetViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private var populateJob: Job? = null

    private val _state: MutableStateFlow<CategoryBottomSheetState> =
        MutableStateFlow(CategoryBottomSheetState.Loading)
    val state = _state.asStateFlow()

    var type: CategoryBottomSheetType = CategoryBottomSheetType.UNSPECIFIED
        set(value) {
            field = value
            populateJob?.cancel()
            populateJob = populateJob()
        }

    fun populateJob() = viewModelScope.launch {
        val result = when (type) {
            CategoryBottomSheetType.UNSPECIFIED -> {
                categoriesRepository.getAllCategories()
            }

            CategoryBottomSheetType.EXPENSES -> {
                categoriesRepository.getAllExpenseCategories()
            }

            CategoryBottomSheetType.INCOMES -> {
                categoriesRepository.getAllIncomeCategories()
            }
        }.first()

        when (result) {
            is SafeResult.Failure -> {
                _state.value = CategoryBottomSheetState.Error()
            }

            is SafeResult.Success<List<Category>> -> {
                _state.value =
                    CategoryBottomSheetState.Loaded(result.data.map { it.toCategoryData() })
            }
        }
    }
}
