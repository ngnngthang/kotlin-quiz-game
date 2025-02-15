package com.example.quiz.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.ui.theme.LightText
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTopBar(navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = LightText,
        ),
        title = {
            Text("Quiz Results", fontSize = 20.sp, fontWeight = FontWeight(600))
        },
        navigationIcon = {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = LightText,
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "Back",
                )
            }
        }
    )
}
@Composable
fun ScoreScreen(
    navController: NavHostController,
    score: Int,
    totalQuestions: Int,
    userAnswers: String, // Serialized user answers
    correctAnswers: String, // Serialized correct answers
    randomQuestions: String, // Serialized randomly selected questions
    randomChoices: String // Serialized randomly shuffled choices
) {
    // Parse the serialized strings back into lists
    val userAnswersList = userAnswers.split("|")
    val correctAnswersList = correctAnswers.split("|").map { it.toInt() }
    val questionsList = randomQuestions.split("|")
    val choicesList = randomChoices.split("|").map { it.split(",") }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { ScoreTopBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            // Display the score
            Text(
                "Your score is $score/$totalQuestions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Display the questions and answers
            for (i in 0 until totalQuestions) {
                Text(
                    "Question ${i + 1}: ${questions[i]}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    "Your answer: ${userAnswersList[i] ?: "No answer"}",
                    fontSize = 16.sp,
                    color = if (userAnswersList[i] == choices[i][correctAnswersList[i] - 1]) Color.Green else Color.Red
                )
                Text(
                    "Correct answer: ${choices[i][correctAnswersList[i] - 1]}",
                    fontSize = 16.sp,
                    color = Color.Green
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }

            // Go Back button
            Button(
                modifier = Modifier.padding(top = 20.dp),
                onClick = { navController.popBackStack() }
            ) {
                Text("Go Back", fontSize = 16.sp, fontWeight = FontWeight(400))
            }
        }
    }
}