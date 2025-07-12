package com.example.mathformula.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCategoryClick: (Long, String) -> Unit,
    onSearchClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val refreshing = false // offline sample, nothing to refresh
    val pullRefreshState = rememberPullRefreshState(refreshing, {})

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Math Formulas") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .pullRefresh(pullRefreshState)
            .padding(paddingValues)
        ) {
            when (val state = uiState) {
                HomeUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                is HomeUiState.Success -> CategoryList(
                    categories = state.categories,
                    onCategoryClick = onCategoryClick,
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                )
                is HomeUiState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun CategoryList(
    categories: List<com.example.mathformula.data.entity.CategoryEntity>,
    onCategoryClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        categories.forEach { category ->
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClick(category.id, category.name) }
                    .padding(vertical = 12.dp)
            )
            Divider()
        }
    }
}