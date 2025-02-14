package com.example.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quiz.features.MainScreen
import com.example.quiz.features.QuizScreen
import com.example.quiz.features.ScoreScreen
import com.example.quiz.features.WelcomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "greeting"
    ) {
        composable("greeting") {
            WelcomeScreen(navController = navController)
        }
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("quiz") {
            QuizScreen(navController = navController)
        }
        composable("score/{score}/{totalQuestions}") { backStackEntry ->
            // Get score and totalQuestions from arguments and convert to Int
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val totalQuestions = backStackEntry.arguments?.getString("totalQuestions")?.toInt() ?: 0
            ScoreScreen(navController = navController, score = score, totalQuestions = totalQuestions)
        }
    }
}