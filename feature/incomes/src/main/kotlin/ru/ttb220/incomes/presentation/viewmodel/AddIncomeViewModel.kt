package ru.ttb220.incomes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ttb220.incomes.presentation.model.AddIncomeIntent
import ru.ttb220.incomes.presentation.model.AddIncomeScreenState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddIncomeViewModel @Inject constructor(

) : ViewModel() {

    private val _screenState = MutableStateFlow<AddIncomeScreenState>(AddIncomeScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    fun onAccountSelectorClick() {

    }

    fun onCategorySelectorClick() {

    }

    fun onAmountChange(newAmount: String) {

    }

    fun onDateSelectorClick() {

    }

    fun onTimeChange(newTime: String) {

    }

    fun onCommentChange(newComment: String) {

    }

    fun onIntent(intent: AddIncomeIntent) {

    }
}