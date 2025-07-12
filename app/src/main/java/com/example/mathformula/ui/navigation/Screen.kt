package com.example.mathformula.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Category : Screen("category/{categoryId}/{categoryName}") {
        fun createRoute(id: Long, name: String) = "category/$id/$name"
    }
    object Formula : Screen("formula/{formulaId}") {
        fun createRoute(id: Long) = "formula/$id"
    }

    object Search : Screen("search")
}