package com.example.quiz.features

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.LightText
import com.example.quiz.ui.theme.Primary

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.z6297460532784_de7a505f016cf5432344a38d2e2ce31d),
            contentDescription = "App background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Android Logo",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Welcome to Hack's Game",
                color = DarkText,
                fontSize = 32.sp,
                fontWeight = FontWeight(600),
                modifier = Modifier.padding(16.dp)
            )
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
                onClick = { navController.navigate("main") },
            ) {
                Text(
                    text = "OK", fontSize = 18.sp, fontWeight = FontWeight(600)
                )
            }
        }
    }
}