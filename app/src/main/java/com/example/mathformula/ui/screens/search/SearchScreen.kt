package com.example.mathformula.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mathformula.ui.components.LatexBlock
import com.example.mathformula.data.entity.FormulaWithCategory
import androidx.compose.runtime.*
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onFormulaClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val refreshing = false // offline, no real refresh
    val pullRefreshState = rememberPullRefreshState(refreshing, {})

    Scaffold(
        topBar = {
            SearchTopBar(onQueryChange = viewModel::setQuery)
        },
        modifier = modifier
    ) { paddingValues ->
        Box(Modifier.pullRefresh(pullRefreshState)) {
            when (val state = uiState) {
                SearchUiState.Idle -> {
                    Text(
                        text = "Start typing to search formulas",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is SearchUiState.Success -> SearchResultList(
                    results = state.results,
                    onFormulaClick = onFormulaClick,
                    modifier = Modifier.padding(paddingValues)
                )
                is SearchUiState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                SearchUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(androidx.compose.ui.Alignment.TopCenter))
        }
    }
}

@Composable
private fun SearchTopBar(onQueryChange: (String) -> Unit) {
    var searchValue by remember { mutableStateOf(TextFieldValue("")) }

    TopAppBar(
        title = { Text(text = "Search") },
        navigationIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        actions = {
            OutlinedTextField(
                value = searchValue,
                onValueChange = {
                    searchValue = it
                    onQueryChange(it.text)
                },
                placeholder = { Text("Enter keyword") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )
        }
    )
}

@Composable
private fun SearchResultList(
    results: List<FormulaWithCategory>,
    onFormulaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(results) { item ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { onFormulaClick(item.formula.id) }
                .padding(16.dp)) {
                Text(text = item.formula.title, style = MaterialTheme.typography.titleMedium)
                LatexBlock(latex = item.formula.latex, modifier = Modifier.heightIn(min = 32.dp))
            }
            Divider()
        }
    }
}