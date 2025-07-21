package com.example.quizapp2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp2.ui.screens.HomeScreen
import com.example.quizapp2.ui.screens.QuizScreen
import com.example.quizapp2.ui.screens.ResultScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Quiz : Screen("quiz")
    object Result : Screen("result/{score}") {
        const val ARG_SCORE = "score"
        fun createRoute(score: Int) = "result/$score"
    }
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Quiz
        composable(Screen.Quiz.route) {
            QuizScreen(
                onQuizComplete = { score ->
                    navController.navigate(Screen.Result.createRoute(score))
                }
            )
        }

        // Result
        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument(Screen.Result.ARG_SCORE) { defaultValue = 0 }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments
                ?.getInt(Screen.Result.ARG_SCORE) ?: 0

            ResultScreen(
                navController = navController,
                score = score
            )
        }
    }
}
