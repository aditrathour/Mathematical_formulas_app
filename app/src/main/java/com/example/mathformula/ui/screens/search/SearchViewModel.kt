package com.example.mathformula.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathformula.data.entity.FormulaWithCategory
import com.example.mathformula.data.repository.FormulaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FormulaRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    fun setQuery(value: String) {
        query.value = value
    }

    val uiState: StateFlow<SearchUiState> = query
        .flatMapLatest { q ->
            if (q.isBlank()) flowOf(emptyList())
            else repository.searchFormulas(q)
        }
        .map<SearchUiState> { SearchUiState.Success(it) }
        .catch { emit(SearchUiState.Error(it.message ?: "Unknown error")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SearchUiState.Idle)
}

sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val results: List<FormulaWithCategory>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}