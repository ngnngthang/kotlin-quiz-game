package com.example.quiz.features


import android.content.Context

// Function to save the score to SharedPreferences
fun saveScore(context: Context, score: Int) {
    val sharedPreferences = context.getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("last_score", score)
    editor.apply()
}


// Function to retrieve the last score from SharedPreferences
fun getLastScore(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("last_score", 0)  // Default to 0 if no score is found
}

fun saveIsPlayed(context: Context, state: Boolean) {
    val sharedPreferences = context.getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("is_played", state)
    editor.apply()
}

fun getIsPlayed(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("is_played", false)
}