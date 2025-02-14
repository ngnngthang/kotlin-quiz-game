package com.example.quiz.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.LightText
import com.example.quiz.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    fun onStart() {
        navController.navigate("quiz")
    }

    fun onBack() {
        navController.popBackStack()
    }

    fun onViewScores() {
        navController.navigate("score")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz") },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back",
                    )
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        innerPadding
                    )
                    .background(DarkText)
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
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
                            "Start", fontSize = 24.sp,
                            fontFamily = FontFamily(fonts = listOf(Font(R.font.jersey10_regular))),

                            )
                    }
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
                            onViewScores()
                        }
                    ) {
                        Text(
                            "Show score", fontSize = 24.sp,
                            fontFamily = FontFamily(fonts = listOf(Font(R.font.jersey10_regular))),

                            )
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = LightText,
                            contentColor = DarkText,
                            disabledContentColor = Color.Gray,
                            disabledContainerColor = Color.Gray
                        ),
                        contentPadding = PaddingValues(16.dp),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            onBack()
                        }
                    ) {
                        Text(
                            "Back", fontSize = 24.sp,
                            fontFamily = FontFamily(fonts = listOf(Font(R.font.jersey10_regular))),

                            )
                    }
                }
            }
        }
    )

}