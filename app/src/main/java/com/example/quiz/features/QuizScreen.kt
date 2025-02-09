package com.example.quiz.features

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.LightText
import com.example.quiz.ui.theme.Primary
import com.example.quiz.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavHostController) {


    val selectedAnswer = remember { mutableStateOf<String?>(null) }
    val answers = listOf("Ottawa", "Toronto", "Vancouver", "Montreal")

    fun onSelectAnswer(answer: String) {
        println("Selecting answer: $answer")
        selectedAnswer.value = answer
        println("Selected answer: ${selectedAnswer.value}")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(), topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = LightText,
                ),
                title = {
                    Text("Quiz", fontSize = 20.sp, fontWeight = FontWeight(600))
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
                },

                )
        }
    ) { innerPadding ->
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = innerPadding.calculateTopPadding() + 10.dp,
                        horizontal = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Question 1",
                    fontSize = 20.sp, fontWeight = FontWeight(500), color = DarkText
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    "What is the capital of Canada?",
                    fontSize = 24.sp, fontWeight = FontWeight(500), color = DarkText
                )
                Spacer(modifier = Modifier.padding(20.dp))
                answers.forEach { answer ->
                    AnswerButton(
                        answer = answer,
                        isSelected = selectedAnswer.value == answer,
                        onClick = { onSelectAnswer(answer) }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = DarkText
                        ),
                        border = BorderStroke(width = 1.dp, color = DarkText),
                        onClick = {}
                    ) {
                        Text("Back", fontSize = 16.sp, fontWeight = FontWeight(400))
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = LightText
                        ),
                        border = BorderStroke(width = 1.dp, color = Primary),
                        onClick = {}
                    ) {
                        Text("Next", fontSize = 16.sp, fontWeight = FontWeight(400))
                    }
                }
            }

        }
    }
}

@Composable
fun AnswerButton(answer: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = if (isSelected) Purple80 else DarkText),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Purple80 else Color.Transparent,
            contentColor = DarkText
        )
    ) {
        Text(answer, fontSize = 16.sp, fontWeight = FontWeight(400))
    }
}