package com.mathformulas.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mathformulas.app.ui.navigation.Screen
import com.mathformulas.app.viewmodel.FormulaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: FormulaViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredFormulas by viewModel.filteredFormulas.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    var localSearchQuery by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Search Formulas",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                query = localSearchQuery,
                onQueryChange = { newQuery ->
                    localSearchQuery = newQuery
                    viewModel.searchFormulas(newQuery)
                },
                onClear = {
                    localSearchQuery = ""
                    viewModel.searchFormulas("")
                },
                modifier = Modifier.padding(16.dp)
            )
            
            // Search Results
            if (localSearchQuery.isBlank()) {
                // Show search suggestions when no query
                SearchSuggestions(
                    onSuggestionClick = { suggestion ->
                        localSearchQuery = suggestion
                        viewModel.searchFormulas(suggestion)
                    }
                )
            } else if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredFormulas.isEmpty()) {
                NoResultsFound(
                    query = localSearchQuery,
                    onClearSearch = {
                        localSearchQuery = ""
                        viewModel.searchFormulas("")
                    }
                )
            } else {
                SearchResults(
                    formulas = filteredFormulas,
                    query = localSearchQuery,
                    onFormulaClick = { formula ->
                        navController.navigate(
                            Screen.FormulaDetail.createRoute(formula.id)
                        )
                    },
                    onFavoriteClick = { formula ->
                        viewModel.toggleFavorite(formula)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text("Search formulas...") },
        placeholder = { Text("Enter formula name, concept, or keyword") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors()
    )
}

@Composable
fun SearchSuggestions(
    onSuggestionClick: (String) -> Unit
) {
    val suggestions = listOf(
        "quadratic", "triangle", "derivative", "integral", "sine", "cosine",
        "area", "volume", "probability", "statistics", "algebra", "geometry",
        "calculus", "trigonometry", "physics", "chemistry"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Popular Searches",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            suggestions.chunked(2).forEach { rowSuggestions ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowSuggestions.forEach { suggestion ->
                        SuggestionChip(
                            text = suggestion,
                            onClick = { onSuggestionClick(suggestion) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill remaining space if odd number of suggestions
                    if (rowSuggestions.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestionChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun NoResultsFound(
    query: String,
    onClearSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.SearchOff,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "No formulas found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "for \"$query\"",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Try searching for:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "• Formula names (e.g., \"quadratic\")\n• Mathematical concepts (e.g., \"derivative\")\n• Categories (e.g., \"algebra\")\n• Keywords (e.g., \"triangle\")",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedButton(
                onClick = onClearSearch
            ) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear Search")
            }
        }
    }
}

@Composable
fun SearchResults(
    formulas: List<com.mathformulas.app.data.Formula>,
    query: String,
    onFormulaClick: (com.mathformulas.app.data.Formula) -> Unit,
    onFavoriteClick: (com.mathformulas.app.data.Formula) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "${formulas.size} results for \"$query\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(formulas) { formula ->
            FormulaCard(
                formula = formula,
                onClick = { onFormulaClick(formula) },
                onFavoriteClick = { onFavoriteClick(formula) }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}