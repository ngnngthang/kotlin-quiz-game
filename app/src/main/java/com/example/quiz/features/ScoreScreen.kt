package com.example.quiz.features

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.Green
import com.example.quiz.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTopBar(navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = DarkText,
        ),
        title = {
            Text(
                "Quiz Results",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.jersey10_regular))
            )
        },
        navigationIcon = {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = DarkText,
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "Back",
                )
            }
        },
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
    Log.d("ScoreScreen", "score: $score")
    Log.d("ScoreScreen", "userAnswers: $userAnswers")
    Log.d("ScoreScreen", "correctAnswers: $correctAnswers")
    Log.d("ScoreScreen", "randomQuestions: $randomQuestions")
    Log.d("ScoreScreen", "randomChoices: $randomChoices")

    val userAnswersList = userAnswers.split("|").map { it -> if (it == "null") null else it }
    val correctAnswersList = correctAnswers.split("|").map { it.toInt() }
    val questionsList = randomQuestions.split("|")
    val choicesList = randomChoices.split("|").map { it.split(",") }

    Log.d("ScoreScreen", "userAnswersList: $userAnswersList")
    Log.d("ScoreScreen", "correctAnswersList: $correctAnswersList")
    Log.d("ScoreScreen", "questionsList: $questionsList")
    Log.d("ScoreScreen", "randomChoices: $randomChoices")

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { ScoreTopBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = innerPadding.calculateTopPadding() + 10.dp,
                    horizontal = 16.dp
                ),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Your score is $score/$totalQuestions",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            for (i in 0 until totalQuestions) {
                Text(
                    text = "Question ${i + 1}: ${questionsList[i]}",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "Your answer: ${userAnswersList[i] ?: "N/A"}",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                    color = if (userAnswersList[i] == choicesList[i][correctAnswersList[i] - 1]) Green else Primary
                )
                Text(
                    text = "Correct answer: ${choicesList[i][correctAnswersList[i] - 1]}",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                    color = Green
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            // Go Back button
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = { navController.popBackStack(route = "main", inclusive = false) },
            ) {
                Text(
                    "Go Back",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.jersey10_regular))
                )
            }
        }
    }
}