package com.example.mathformula.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathformula.data.entity.CategoryEntity
import com.example.mathformula.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    categoryRepository: CategoryRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = categoryRepository.getCategories()
        .map { HomeUiState.Success(it) as HomeUiState }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState.Loading)
}

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val categories: List<CategoryEntity>) : HomeUiState
}