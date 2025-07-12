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
import com.mathformulas.app.data.Formula
import com.mathformulas.app.ui.navigation.Screen
import com.mathformulas.app.viewmodel.FormulaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryName: String,
    navController: NavController,
    viewModel: FormulaViewModel
) {
    val filteredFormulas by viewModel.filteredFormulas.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    // Filter by category when screen is first loaded
    LaunchedEffect(categoryName) {
        viewModel.filterByCategory(categoryName)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        categoryName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredFormulas.isEmpty()) {
            EmptyCategoryState(
                categoryName = categoryName,
                onExploreClick = {
                    navController.navigate(Screen.FormulaList.route)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryHeader(
                        categoryName = categoryName,
                        count = filteredFormulas.size,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(filteredFormulas) { formula ->
                    FormulaCard(
                        formula = formula,
                        onClick = {
                            navController.navigate(
                                Screen.FormulaDetail.createRoute(formula.id)
                            )
                        },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(formula)
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(
    categoryName: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    val categoryColor = getCategoryColor(categoryName)
    val categoryIcon = getCategoryIcon(categoryName)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = categoryColor.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                categoryIcon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = categoryColor
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = categoryColor
                )
                
                Text(
                    text = "$count ${if (count == 1) "formula" else "formulas"} available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = categoryColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun EmptyCategoryState(
    categoryName: String,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                getCategoryIcon(categoryName),
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
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "No formulas are available in the $categoryName category yet. Check back later for new additions!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedButton(
                onClick = onExploreClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.Default.Explore,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Explore Other Categories")
            }
        }
    }
}