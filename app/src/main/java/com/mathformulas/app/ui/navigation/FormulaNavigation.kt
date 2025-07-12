package com.mathformulas.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.mathformulas.app.ui.screens.FormulaDetailScreen
import com.mathformulas.app.ui.screens.FormulaListScreen
import com.mathformulas.app.ui.screens.HomeScreen
import com.mathformulas.app.ui.screens.FavoritesScreen
import com.mathformulas.app.ui.screens.CategoryScreen
import com.mathformulas.app.ui.screens.SearchScreen
import com.mathformulas.app.viewmodel.FormulaViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FormulaList : Screen("formula_list")
    object FormulaDetail : Screen("formula_detail/{formulaId}") {
        fun createRoute(formulaId: Int) = "formula_detail/$formulaId"
    }
    object Favorites : Screen("favorites")
    object Category : Screen("category/{categoryName}") {
        fun createRoute(categoryName: String) = "category/$categoryName"
    }
    object Search : Screen("search")
}

@Composable
fun FormulaNavigation(
    navController: NavHostController,
    viewModel: FormulaViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        
        composable(Screen.FormulaList.route) {
            FormulaListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        
        composable(
            Screen.FormulaDetail.route,
            arguments = listOf(navArgument("formulaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val formulaId = backStackEntry.arguments?.getInt("formulaId") ?: 0
            FormulaDetailScreen(
                formulaId = formulaId,
                navController = navController,
                viewModel = viewModel
            )
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        
        composable(
            Screen.Category.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryScreen(
                categoryName = categoryName,
                navController = navController,
                viewModel = viewModel
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}