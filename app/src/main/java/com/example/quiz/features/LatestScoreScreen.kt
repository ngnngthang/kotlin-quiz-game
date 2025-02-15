package com.example.quiz.features

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestScoreScreen(navController: NavHostController) {
    val context = LocalContext.current

    val lastScore = remember { mutableIntStateOf(0) }
    val isPlayed = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        isPlayed.value = getIsPlayed(context)  // Check if the quiz has been played
        lastScore.intValue = getLastScore(context)  // Retrieve the score
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = DarkText,
                ),
                title = {
                    Text(
                        "Latest Score", fontSize = 28.sp,
                        fontFamily =
                        FontFamily(Font(R.font.jersey10_regular))
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    vertical = innerPadding.calculateTopPadding() + 10.dp,
                    horizontal = 16.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (isPlayed.value) {
                    Text(
                        text = "Your last score: ${lastScore.intValue}",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.jersey10_regular))
                    )
                } else {
                    Text(
                        text = "You haven't played yet",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.jersey10_regular))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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
                        navController.popBackStack()
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


}