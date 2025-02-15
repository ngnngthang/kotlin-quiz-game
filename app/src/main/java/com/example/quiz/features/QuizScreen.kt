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
import androidx.compose.ui.platform.LocalContext
import android.os.CountDownTimer
import androidx.compose.runtime.LaunchedEffect




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
    Color(0xFFffccbc),  // Light Pink
    Color(0xFFbbdefb),  // Light Blue
    Color(0xFFc8e6c9),  // Light Green
    Color(0xFFfff9c4),  // Light Yellow
    Color(0xFFf8bbd0),  // Light Purple
    // Add more colors if you have more than 4 questions
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavHostController) {

    // Randomly shuffle and take 4 questions
    val randomQuestions = questions.shuffled().take(4)
    val randomChoices = randomQuestions.mapIndexed { index, _ ->
        choices[questions.indexOf(randomQuestions[index])].shuffled() // Shuffle the answers for each question
    }
    val randomCorrectAnswers = randomQuestions.mapIndexed { index, _ ->
        val originalCorrectIndex = correctAnswers[questions.indexOf(randomQuestions[index])] - 1
        val correctAnswer = choices[questions.indexOf(randomQuestions[index])][originalCorrectIndex]
        randomChoices[index].indexOf(correctAnswer) + 1 // Add 1 to match your 1-based index
    }

    // Track the current question index and the user's score
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val score = remember { mutableStateOf(0) }


    // Track selected answer for the current question
    val selectedAnswer = remember { mutableStateOf<String?>(null) }

    // Track the time left for each question (in seconds)
    val timeLeft = remember { mutableStateOf(10) }

    // State to track quiz completion
    val quizEnded = remember { mutableStateOf(false) }

    // Get context here, inside composable function
    val context = LocalContext.current

    val timer = remember { mutableStateOf<CountDownTimer?>(null) }

    // Add this to your QuizScreen composable
    val userAnswers = remember { mutableStateOf<List<String?>>(List(randomQuestions.size) { null }) }

    // Function to go to the next question
    val goToNextQuestion: () -> Unit = {
        if (selectedAnswer.value != null || timeLeft.value == 0) {

            userAnswers.value = userAnswers.value.toMutableList().apply {
                this[currentQuestionIndex.value] = selectedAnswer.value
            }

            // Check if the selected answer is correct
            val correctAnswerIndex = randomCorrectAnswers[currentQuestionIndex.value] - 1 // Convert to 0-based index
            if (randomChoices[currentQuestionIndex.value].indexOf(selectedAnswer.value) == correctAnswerIndex) {
                score.value += 1
            }
            // Go to the next question
            if (currentQuestionIndex.value < randomQuestions.size - 1) {
                currentQuestionIndex.value += 1
                selectedAnswer.value = null
                timeLeft.value = 10  // Reset time for next question
            } else {
                // End of quiz, save score and navigate to results screen
                saveScore(context = context, score.value)

                // Serialize questions and choices
                val serializedRandomQuestions = randomQuestions.joinToString("|")
                val serializedRandomChoices = randomChoices.joinToString("|") { it.joinToString(",") }

                // Navigate to ScoreScreen with all required arguments
                navController.navigate(
                    "score/${score.value}/${randomQuestions.size}/" +
                            "${userAnswers.value.joinToString("|")}/" +
                            "${randomCorrectAnswers.joinToString("|")}/" +
                            "$serializedRandomQuestions/$serializedRandomChoices"
                )
                quizEnded.value = true
            }
        }
    }

    // Timer logic

    LaunchedEffect(currentQuestionIndex.value) {
        // Cancel any previous timer before starting a new one
        timer.value?.cancel()

        // Start a new timer countdown for 10 seconds when the question changes
        val newTimer = object : CountDownTimer(10 * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                // When timer finishes, go to the next question
                goToNextQuestion()
            }
        }
        // Start the new timer and store it
        newTimer.start()
        timer.value = newTimer
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),containerColor = colorsList[currentQuestionIndex.value % colorsList.size],
        topBar = {
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
                    "Question ${currentQuestionIndex.value + 1}",
                    fontSize = 20.sp, fontWeight = FontWeight(500), color = DarkText
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    randomQuestions[currentQuestionIndex.value],
                    fontSize = 24.sp, fontWeight = FontWeight(500), color = DarkText
                )
                Spacer(modifier = Modifier.padding(20.dp))

                // Display answer choices
                val questionColor = colorsList[currentQuestionIndex.value % colorsList.size]
                randomChoices[currentQuestionIndex.value].forEach { answer ->
                    AnswerButton(
                        answer = answer,
                        isSelected = selectedAnswer.value == answer,
                        onClick = { selectedAnswer.value = answer },
                        buttonColor = questionColor
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }

                // Display timer
                Text("Time Left: ${timeLeft.value}s", fontSize = 18.sp, color = DarkText)

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
                            containerColor = Color.Transparent,
                            contentColor = DarkText
                        ),
                        border = BorderStroke(width = 1.dp, color = DarkText),
                        onClick = { goToNextQuestion() } // Direct call without navController or context
                    ) {
                        Text("Ok", fontSize = 16.sp, fontWeight = FontWeight(400))
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerButton(answer: String, isSelected: Boolean, onClick: () -> Unit, buttonColor: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = if (isSelected) Purple80 else DarkText),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Purple80 else buttonColor, // Change background color here
            contentColor = DarkText
        )
    ) {
        Text(answer, fontSize = 16.sp, fontWeight = FontWeight(400))
    }
}
