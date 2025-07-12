package com.mathformulas.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mathformulas.app.data.Formula
import com.mathformulas.app.ui.theme.*
import com.mathformulas.app.viewmodel.FormulaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaDetailScreen(
    formulaId: Int,
    navController: NavController,
    viewModel: FormulaViewModel
) {
    var formula by remember { mutableStateOf<Formula?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(formulaId) {
        formula = viewModel.getFormulaById(formulaId)
        isLoading = false
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = formula?.name ?: "Loading...",
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
                    formula?.let { currentFormula ->
                        IconButton(
                            onClick = { 
                                viewModel.toggleFavorite(currentFormula)
                                // Update the local state to reflect the change
                                formula = currentFormula.copy(isFavorite = !currentFormula.isFavorite)
                            }
                        ) {
                            Icon(
                                if (currentFormula.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (currentFormula.isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = if (currentFormula.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
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
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            formula?.let { currentFormula ->
                FormulaDetailContent(
                    formula = currentFormula,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Formula not found",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun FormulaDetailContent(
    formula: Formula,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Formula Header Card
            FormulaHeaderCard(formula = formula)
        }
        
        item {
            // Main Formula Display
            FormulaDisplayCard(formula = formula)
        }
        
        item {
            // Concept Section
            ConceptSection(concept = formula.concept)
        }
        
        item {
            // Variables Section
            formula.variables?.let { variables ->
                VariablesSection(variables = variables)
            }
        }
        
        item {
            // Tips Section
            TipsSection(tips = formula.tips)
        }
        
        item {
            // Tricks Section
            TricksSection(tricks = formula.tricks)
        }
        
        item {
            // Examples Section
            ExamplesSection(examples = formula.examples)
        }
        
        item {
            // Units Section
            formula.units?.let { units ->
                UnitsSection(units = units)
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FormulaHeaderCard(formula: Formula) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getCategoryColor(formula.category).copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                getCategoryIcon(formula.category),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = getCategoryColor(formula.category)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = formula.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = formula.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoChip(
                    label = "Category",
                    value = formula.category,
                    color = getCategoryColor(formula.category)
                )
                
                InfoChip(
                    label = "Difficulty",
                    value = formula.difficulty,
                    color = when (formula.difficulty) {
                        "Easy" -> EasyColor
                        "Medium" -> MediumColor
                        "Hard" -> HardColor
                        else -> ExpertColor
                    }
                )
                
                formula.subcategory?.let { subcategory ->
                    InfoChip(
                        label = "Subcategory",
                        value = subcategory,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.2f)),
            color = Color.Transparent
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = color,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun FormulaDisplayCard(formula: Formula) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formula",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formula.formula,
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ConceptSection(concept: String) {
    DetailSection(
        title = "Concept",
        content = concept,
        icon = Icons.Default.Lightbulb,
        color = AccentBlue
    )
}

@Composable
fun VariablesSection(variables: String) {
    DetailSection(
        title = "Variables",
        content = variables,
        icon = Icons.Default.Functions,
        color = AccentGreen
    )
}

@Composable
fun TipsSection(tips: String) {
    DetailSection(
        title = "Tips",
        content = tips,
        icon = Icons.Default.TipsAndUpdates,
        color = AccentOrange
    )
}

@Composable
fun TricksSection(tricks: String) {
    DetailSection(
        title = "Tricks",
        content = tricks,
        icon = Icons.Default.Psychology,
        color = AccentRed
    )
}

@Composable
fun ExamplesSection(examples: String) {
    DetailSection(
        title = "Examples",
        content = examples,
        icon = Icons.Default.Calculate,
        color = Purple40
    )
}

@Composable
fun UnitsSection(units: String) {
    DetailSection(
        title = "Units",
        content = units,
        icon = Icons.Default.Straighten,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun DetailSection(
    title: String,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = color
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}