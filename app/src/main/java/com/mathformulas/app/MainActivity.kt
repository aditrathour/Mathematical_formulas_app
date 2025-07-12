package com.mathformulas.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.mathformulas.app.data.FormulaDatabase
import com.mathformulas.app.repository.FormulaRepository
import com.mathformulas.app.ui.navigation.FormulaNavigation
import com.mathformulas.app.ui.theme.MathFormulasTheme
import com.mathformulas.app.viewmodel.FormulaViewModel
import com.mathformulas.app.viewmodel.FormulaViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize database and repository
        val database = FormulaDatabase.getDatabase(this, CoroutineScope(SupervisorJob()))
        val repository = FormulaRepository(database.formulaDao())
        val viewModelFactory = FormulaViewModelFactory(repository)
        
        setContent {
            MathFormulasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: FormulaViewModel = viewModel(factory = viewModelFactory)
                    
                    FormulaNavigation(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MathFormulasTheme {
        // Preview content
    }
}