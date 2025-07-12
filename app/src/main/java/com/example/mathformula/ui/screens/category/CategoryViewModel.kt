package com.example.mathformula.ui.screens.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathformula.data.entity.FormulaWithCategory
import com.example.mathformula.data.repository.FormulaRepository
import com.example.mathformula.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: FormulaRepository
) : ViewModel() {

    private val categoryId: Long = savedStateHandle["categoryId"] ?: 0L
    private val categoryName: String = savedStateHandle["categoryName"] ?: ""

    val header: String get() = categoryName

    val uiState: StateFlow<CategoryUiState> = repository.getFormulasByCategory(categoryId)
        .map { CategoryUiState.Success(it) as CategoryUiState }
        .catch { emit(CategoryUiState.Error(it.message ?: "Error loading formulas")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CategoryUiState.Loading)
}

sealed interface CategoryUiState {
    object Loading : CategoryUiState
    data class Success(val formulas: List<FormulaWithCategory>) : CategoryUiState
    data class Error(val message: String) : CategoryUiState
}