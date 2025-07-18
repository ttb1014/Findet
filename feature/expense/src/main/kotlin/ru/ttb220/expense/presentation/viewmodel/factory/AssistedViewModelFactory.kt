package ru.ttb220.expense.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import ru.ttb220.expense.presentation.viewmodel.EditExpenseViewModel

class AssistedViewModelFactory(
    private val assistedFactory: EditExpenseViewModel.Factory,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val savedStateHandle = extras.createSavedStateHandle()
        return assistedFactory.create(savedStateHandle) as T
    }
}