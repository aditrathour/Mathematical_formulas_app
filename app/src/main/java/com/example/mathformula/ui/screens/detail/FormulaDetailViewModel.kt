package com.example.mathformula.ui.screens.detail

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
class FormulaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: FormulaRepository
) : ViewModel() {

    private val formulaId: Long = savedStateHandle["formulaId"] ?: 0L

    val uiState: StateFlow<FormulaDetailUiState> = repository.getFormula(formulaId)
        .map { FormulaDetailUiState.Success(it) as FormulaDetailUiState }
        .catch { emit(FormulaDetailUiState.Error(it.message ?: "Error")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FormulaDetailUiState.Loading)
}

sealed interface FormulaDetailUiState {
    object Loading : FormulaDetailUiState
    data class Success(val data: FormulaWithCategory) : FormulaDetailUiState
    data class Error(val message: String) : FormulaDetailUiState
}