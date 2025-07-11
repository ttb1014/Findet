package ru.ttb220.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.categories.domain.GetAllCategoriesUseCase
import ru.ttb220.categories.presentation.model.CategoriesScreenState
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.CategoryData
import ru.ttb220.presentation.model.screen.CategoriesScreenData
import ru.ttb220.presentation.util.DomainErrorMessageMapper
import ru.ttb220.presentation.util.EmojiMapper
import javax.inject.Inject
import javax.inject.Singleton

class CategoriesViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
) : ViewModel() {

    private val _categoriesScreenState: MutableStateFlow<CategoriesScreenState> =
        MutableStateFlow(CategoriesScreenState.Loading)
    val categoriesScreenState = _categoriesScreenState.asStateFlow()

    private var allCategories: List<CategoryData> = emptyList()

    // Загружает все доступные категории. В случае успеха изменяет allCategories и состояние UI
    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val result = loadAllCategories()
        when (result) {
            is SafeResult.Failure -> {
                _categoriesScreenState.value = CategoriesScreenState.ErrorResource(
                    DomainErrorMessageMapper.toMessageRes(result.cause)
                )
            }

            is SafeResult.Success -> {
                allCategories = result.data
                updateCurrentState()
            }
        }
    }

    // Проверяет доступ в интернет, грузит результат и возвращает его
    private suspend fun loadAllCategories(): SafeResult<List<CategoryData>> {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            return SafeResult.Failure(
                cause = DomainError.NoInternet
            )
        }

        val categoriesResult = getAllCategoriesUseCase().first()
        return when (categoriesResult) {
            is SafeResult.Failure -> {
                SafeResult.Failure(categoriesResult.cause)
            }

            is SafeResult.Success -> {
                val categoryDataList = categoriesResult.data.map { category ->
                    CategoryData(
                        name = category.name,
                        emojiData = EmojiMapper.getEmojiData(category.emoji)
                    )
                }
                SafeResult.Success(categoryDataList)
            }
        }
    }

    // Обновляет состояние UI, чтобы отобразить ВСЕ категории
    private fun updateCurrentState() {
        _categoriesScreenState.value = CategoriesScreenState.Loaded(
            data = CategoriesScreenData(
                data = allCategories
            )
        )
    }

    // Задаёт состояние UI из тех категорий, которые подходят под запрос
    fun onSearch(query: String) {

        val filteredCategories =
            allCategories.filter { category ->
                category.name.contains(query, ignoreCase = true)
            }

        _categoriesScreenState.value = CategoriesScreenState.Loaded(
            data = CategoriesScreenData(
                data = filteredCategories
            )
        )
    }

    init {
        tryLoadAndUpdateState()
    }
}