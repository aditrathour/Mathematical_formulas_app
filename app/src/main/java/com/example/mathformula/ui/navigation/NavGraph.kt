package com.example.mathformula.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mathformula.ui.screens.home.HomeScreen
import com.example.mathformula.ui.screens.detail.FormulaDetailScreen
import com.example.mathformula.ui.screens.category.CategoryScreen

@Composable
fun MathNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCategoryClick = { id, name ->
                    navController.navigate(Screen.Category.createRoute(id, name))
                }
            )
        }

        composable(
            route = Screen.Category.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.LongType },
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            CategoryScreen(onFormulaClick = { formulaId ->
                navController.navigate(Screen.Formula.createRoute(formulaId))
            })
        }

        composable(
            route = Screen.Formula.route,
            arguments = listOf(navArgument("formulaId") { type = NavType.LongType })
        ) { backStackEntry ->
            FormulaDetailScreen()
        }
    }
}