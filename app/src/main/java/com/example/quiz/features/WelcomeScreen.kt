package com.example.quiz.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.LightText
import com.example.quiz.ui.theme.Primary

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Scaffold { innnerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innnerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Android Logo",
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "WELCOME TO TRIVIA GAME OF KATE",
                    color = DarkText,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(fonts = listOf(Font(R.font.jersey10_regular))),
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
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
                    contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = { navController.navigate("main") },
                ) {
                    Text(
                        text = "START",
                        fontSize = 28.sp,
                        fontFamily = FontFamily(fonts = listOf(Font(R.font.jersey10_regular)))
                    )
                }
            }
        }
    }

}