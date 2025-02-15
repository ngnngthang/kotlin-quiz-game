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
        composable(
            "score/{score}/{totalQuestions}/{userAnswers}/{correctAnswers}/{randomQuestions}/{randomChoices}"
        ) { backStackEntry ->
            // Get arguments from the navigation route
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val totalQuestions = backStackEntry.arguments?.getString("totalQuestions")?.toInt() ?: 0
            val userAnswers = backStackEntry.arguments?.getString("userAnswers") ?: ""
            val correctAnswers = backStackEntry.arguments?.getString("correctAnswers") ?: ""
            val randomQuestions = backStackEntry.arguments?.getString("randomQuestions") ?: ""
            val randomChoices = backStackEntry.arguments?.getString("randomChoices") ?: ""


            // Pass all arguments to ScoreScreen
            ScoreScreen(
                navController = navController,
                score = score,
                totalQuestions = totalQuestions,
                userAnswers = userAnswers,
                correctAnswers = correctAnswers,
                randomQuestions = randomQuestions,
                randomChoices = randomChoices
            )
        }
    }
}