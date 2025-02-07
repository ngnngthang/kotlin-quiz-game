package com.example.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quiz.Greeting
import com.example.quiz.Quiz

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "greeting"
    ) {
        composable("greeting") {
            Greeting(navController = navController)
        }
        composable("quiz") {
            Quiz(navController = navController)
        }
    }
}