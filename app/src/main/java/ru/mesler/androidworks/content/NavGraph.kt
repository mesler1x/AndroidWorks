package ru.mesler.androidworks.content

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("profile") { ProfileScreen(navController) }
        composable("list") { ListScreen(navController) }
        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
            DetailsScreen(navController, movieId)
        }
    }
}
