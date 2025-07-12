package com.mathformulas.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mathformulas.app.data.Formula
import com.mathformulas.app.repository.FormulaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormulaViewModel(private val repository: FormulaRepository) : ViewModel() {
    
    // UI State
    private val _uiState = MutableStateFlow(FormulaUiState())
    val uiState: StateFlow<FormulaUiState> = _uiState.asStateFlow()
    
    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // Selected category
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()
    
    // All formulas
    val allFormulas = repository.getAllFormulas().asLiveData()
    
    // Favorite formulas
    val favoriteFormulas = repository.getFavoriteFormulas().asLiveData()
    
    // Categories
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()
    
    // Filtered formulas based on search and category
    private val _filteredFormulas = MutableStateFlow<List<Formula>>(emptyList())
    val filteredFormulas: StateFlow<List<Formula>> = _filteredFormulas.asStateFlow()
    
    init {
        loadCategories()
        loadAllFormulas()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoriesList = repository.getAllCategories()
                _categories.value = listOf("All") + categoriesList
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load categories: ${e.message}"
                )
            }
        }
    }
    
    private fun loadAllFormulas() {
        viewModelScope.launch {
            try {
                repository.getAllFormulas().collect { formulas ->
                    _filteredFormulas.value = formulas
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load formulas: ${e.message}"
                )
            }
        }
    }
    
    fun searchFormulas(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            try {
                if (query.isBlank()) {
                    if (_selectedCategory.value == "All") {
                        repository.getAllFormulas().collect { formulas ->
                            _filteredFormulas.value = formulas
                        }
                    } else {
                        repository.getFormulasByCategory(_selectedCategory.value).collect { formulas ->
                            _filteredFormulas.value = formulas
                        }
                    }
                } else {
                    repository.searchFormulas(query).collect { formulas ->
                        _filteredFormulas.value = if (_selectedCategory.value == "All") {
                            formulas
                        } else {
                            formulas.filter { it.category == _selectedCategory.value }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Search failed: ${e.message}"
                )
            }
        }
    }
    
    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            try {
                if (category == "All") {
                    if (_searchQuery.value.isBlank()) {
                        repository.getAllFormulas().collect { formulas ->
                            _filteredFormulas.value = formulas
                        }
                    } else {
                        repository.searchFormulas(_searchQuery.value).collect { formulas ->
                            _filteredFormulas.value = formulas
                        }
                    }
                } else {
                    repository.getFormulasByCategory(category).collect { formulas ->
                        _filteredFormulas.value = if (_searchQuery.value.isBlank()) {
                            formulas
                        } else {
                            formulas.filter { 
                                it.name.contains(_searchQuery.value, ignoreCase = true) ||
                                it.description.contains(_searchQuery.value, ignoreCase = true) ||
                                it.tags.contains(_searchQuery.value, ignoreCase = true)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Filter failed: ${e.message}"
                )
            }
        }
    }
    
    fun toggleFavorite(formula: Formula) {
        viewModelScope.launch {
            try {
                repository.updateFavoriteStatus(formula.id, !formula.isFavorite)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update favorite: ${e.message}"
                )
            }
        }
    }
    
    suspend fun getFormulaById(id: Int): Formula? {
        return try {
            repository.getFormulaById(id)
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = "Failed to load formula: ${e.message}"
            )
            null
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}

data class FormulaUiState(
    val isLoading: Boolean = true,
    val error: String? = null
)

class FormulaViewModelFactory(private val repository: FormulaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormulaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormulaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}