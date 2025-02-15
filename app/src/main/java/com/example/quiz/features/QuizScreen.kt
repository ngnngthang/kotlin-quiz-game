package com.example.quiz.features

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.example.quiz.R
import com.example.quiz.ui.theme.DarkText
import com.example.quiz.ui.theme.Green
import com.example.quiz.ui.theme.LightText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


// Data for 20 questions
val questions = listOf(
    "What is the capital of Canada?",
    "Which planet is known as the Red Planet?",
    "Who wrote 'Romeo and Juliet'?",
    "What is the largest ocean on Earth?",
    "Which country won the 2018 FIFA World Cup?",
    "Who painted the Mona Lisa?",
    "What is the longest river in the world?",
    "Who is known as the father of modern physics?",
    "Which element has the chemical symbol 'O'?",
    "Which movie won the Academy Award for Best Picture in 1994?",
    "What is the capital of France?",
    "Which continent is known as the 'Dark Continent'?",
    "Which animal is the largest land mammal?",
    "What is the currency of Japan?",
    "What year did the Titanic sink?",
    "Which gas do plants use for photosynthesis?",
    "In what year did World War II end?",
    "Who discovered penicillin?",
    "Which country is the largest by population?",
    "What is the square root of 64?"
)

val choices = listOf(
    listOf("Ottawa", "Toronto", "Vancouver", "Montreal"),
    listOf("Mars", "Earth", "Jupiter", "Saturn"),
    listOf("William Shakespeare", "Charles Dickens", "Mark Twain", "Homer"),
    listOf("Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"),
    listOf("France", "Germany", "Brazil", "Russia"),
    listOf("Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Claude Monet"),
    listOf("Amazon River", "Nile River", "Yangtze River", "Ganges River"),
    listOf("Albert Einstein", "Isaac Newton", "Nikola Tesla", "Marie Curie"),
    listOf("Oxygen", "Hydrogen", "Carbon", "Nitrogen"),
    listOf("Forrest Gump", "The Shawshank Redemption", "Pulp Fiction", "The Godfather"),
    listOf("Paris", "Berlin", "London", "Madrid"),
    listOf("Africa", "Asia", "South America", "Europe"),
    listOf("Elephant", "Rhino", "Giraffe", "Hippo"),
    listOf("Yen", "Won", "Peso", "Dollar"),
    listOf("1912", "1905", "1910", "1915"),
    listOf("Carbon Dioxide", "Oxygen", "Nitrogen", "Hydrogen"),
    listOf("1945", "1941", "1950", "1939"),
    listOf("Alexander Fleming", "Louis Pasteur", "Marie Curie", "Thomas Edison"),
    listOf("China", "India", "USA", "Russia"),
    listOf("8", "6", "10", "4")
)

val correctAnswers = listOf(
    1,  // Ottawa
    1,  // Mars
    1,  // William Shakespeare
    1,  // Pacific Ocean
    1,  // France
    1,  // Leonardo da Vinci
    2,  // Nile River
    1,  // Albert Einstein
    1,  // Oxygen
    1,  // Forrest Gump
    1,  // Paris
    1,  // Africa
    1,  // Elephant
    1,  // Yen
    1,  // 1912
    1,  // Carbon Dioxide
    1,  // 1945
    1,  // Alexander Fleming
    1,  // China
    1   // 8
)

val colorsList = listOf(
    Color(0xFFbbdefb),  // Light Blue
    Color(0xFFf28482),  // Light Yellow
    Color(0xFFc8e6c9),  // Light Green
    Color(0xFFf07167),  // Light Purple
    // Add more colors if you have more than 4 questions
)

val bgColors = listOf(
    Color(0xFF023047),
    Color(0xFF780000),
    Color(0xFF344e41),
    Color(0xFF450920),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavHostController) {
    var randomQuestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var randomChoices by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    var randomCorrectAnswers by remember { mutableStateOf<List<Int>>(emptyList()) }
    var userAnswers by remember { mutableStateOf(List<String?>(randomQuestions.size) { null }) }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(10) }

    val scope = rememberCoroutineScope()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Track the current question index and the user's score
    val score = remember { mutableIntStateOf(0) }
    // Track selected answer for the current question
    val selectedAnswer = remember { mutableStateOf<String?>(null) }

    // Function to go to the next question
    fun goToNextQuestion() {
        if (userAnswers.size > currentQuestionIndex) { // Check size before access
            userAnswers = userAnswers.toMutableList().apply {
                this[currentQuestionIndex] = selectedAnswer.value
            }
        }

        // Check if the selected answer is correct
        val correctAnswerIndex =
            randomCorrectAnswers[currentQuestionIndex]

        if (selectedAnswer.value == randomChoices[currentQuestionIndex][correctAnswerIndex - 1]) {
            score.value += 1
        }

        Log.d("QuizScreen", "User Answers: $userAnswers")
        Log.d("QuizScreen", "Score: ${score.intValue}")
        // Go to the next question
        if (currentQuestionIndex < randomQuestions.size - 1) {
            currentQuestionIndex += 1
            selectedAnswer.value = null
            timeLeft = 10
        } else {
            // End of quiz, save score and navigate to results screen
            saveScore(context = context, score.intValue)
            saveIsPlayed(context = context, state = true)

            // Serialize questions and choices
            val serializedRandomQuestions = randomQuestions.joinToString("|")
            val serializedRandomChoices =
                randomChoices.joinToString("|") { it.joinToString(",") }

            // Navigate to ScoreScreen with all required arguments
            navController.navigate(
                "score/${score.intValue}/${randomQuestions.size}/" +
                        "${userAnswers.joinToString("|")}/" +
                        "${randomCorrectAnswers.joinToString("|")}/" +
                        "$serializedRandomQuestions/$serializedRandomChoices"
            )
        }
    }

    // Initialize questions and choices
    LaunchedEffect(Unit) {
        val randomIndexes = questions.indices.shuffled().take(4)
        randomQuestions = randomIndexes.map { questions[it] }
        randomChoices = randomIndexes.map { choices[it] }
        randomCorrectAnswers = randomIndexes.map { correctAnswers[it] }
        currentQuestionIndex = 0
        userAnswers = List(randomQuestions.size) { null }

        Log.d("QuizScreen", "Random Questions: $randomQuestions")
        Log.d("QuizScreen", "Random Choices: $randomChoices")
        Log.d("QuizScreen", "Random Correct Answers: $randomCorrectAnswers")

        //        val originalCorrectIndex = correctAnswers[questions.indexOf(randomQuestions[index])] - 1
//        val correctAnswer = choices[questions.indexOf(randomQuestions[index])][originalCorrectIndex]
//          randomChoices[index].indexOf(correctAnswer) + 1 // Add 1 to match your 1-based index
//          }
    }

    // Timer logic
    LaunchedEffect(currentQuestionIndex) {
        timeLeft = 10
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow {
                for (i in timeLeft downTo 0) {
                    emit(i)
                    delay(1000)
                }
            }.collect { time ->
                timeLeft = time
                if (time == 0) {
                    goToNextQuestion()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = bgColors[currentQuestionIndex],
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = DarkText,
                ),
                title = {
                    Text(
                        "Quiz", fontSize = 28.sp,
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
        if (randomQuestions.isEmpty() || randomChoices.isEmpty())
            Text(
                modifier = Modifier.padding(innerPadding),
                text = "Loading... ",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                color = LightText
            )
        else
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = innerPadding.calculateTopPadding() + 10.dp,
                        horizontal = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    item {

                        Text(
                            text = "Question ${currentQuestionIndex + 1}",
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                            color = LightText
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            randomQuestions[currentQuestionIndex],
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.jersey10_regular)),
                            color = LightText
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        // Display answer choices
                        randomChoices[currentQuestionIndex].forEach { answer ->
                            AnswerButton(
                                answer = answer,
                                isSelected = selectedAnswer.value == answer,
                                onClick = { selectedAnswer.value = answer },
                                buttonColor = colorsList[currentQuestionIndex]
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                        }

                        // Display timer
                        Text(
                            "Time Left: ${timeLeft}s", fontSize = 32.sp, color = LightText,
                            fontFamily = FontFamily(Font(R.font.jersey10_regular))
                        )

                        // Action buttons (Back and Next)
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
                                    containerColor = Color.White,
                                    contentColor = DarkText
                                ),
                                border = BorderStroke(width = 1.dp, color = DarkText),
                                onClick = { goToNextQuestion() } // Direct call without navController or context
                            ) {
                                Text(
                                    "OK",
                                    fontSize = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.jersey10_regular))
                                )
                            }
                        }
                    }
                }
            )
    }
}

@Composable
fun AnswerButton(answer: String, isSelected: Boolean, onClick: () -> Unit, buttonColor: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = if (isSelected) Green else DarkText),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Green else buttonColor, // Change background color here
            contentColor = LightText
        )
    ) {
        Text(answer, fontSize = 32.sp, fontFamily = FontFamily(Font(R.font.jersey10_regular)))
    }
}
