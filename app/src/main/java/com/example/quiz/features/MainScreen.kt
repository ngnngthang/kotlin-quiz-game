package com.example.quiz.features

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.LightText
import com.example.quiz.ui.theme.Primary


@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val showScore = remember { mutableStateOf(false) }
    val lastScore = remember { mutableStateOf(0) }
    val isPlayed = remember { mutableStateOf(false) }

    fun onStart() {
        navController.navigate("quiz")
    }

    fun onBack() {
        navController.popBackStack()
    }

    fun toggleViewScore() {
        showScore.value = !showScore.value
    }

    LaunchedEffect(Unit) {
        isPlayed.value = getIsPlayed(context)  // Check if the quiz has been played
        lastScore.value = getLastScore(context)  // Retrieve the score
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Start Quiz Button
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Primary,
                    contentColor = LightText,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                contentPadding = PaddingValues(16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onStart()
                }
            ) {
                Text(
                    "START",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular))
                )
            }
            // Show Score Button
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Primary,
                    contentColor = LightText,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                contentPadding = PaddingValues(16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    toggleViewScore() // Fetch the score on click
                }
            ) {
                Text(
                    "SHOW SCORE",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular))
                )
            }
            // Display the last score
            if (showScore.value) {
                Text(
                    text = "Your Latest Score: ${lastScore.value}",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                    color = DarkText,
                    modifier = Modifier.padding(16.dp)
                )
            }
            // Back Button
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = DarkText,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                border = BorderStroke(width = 1.dp, color = Primary),
                contentPadding = PaddingValues(16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onBack()
                }
            ) {
                Text(
                    "BACK",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular))
                )
            }
        }
    }
}