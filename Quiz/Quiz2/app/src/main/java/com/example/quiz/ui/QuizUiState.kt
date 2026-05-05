package com.example.quiz.ui

import com.example.quiz.model.Question
import com.example.quiz.data.DataSource

data class QuizUiState(
    val currentQuestion: Question = DataSource.questions[0],
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = DataSource.questions.size,
    val score: Int = 0,
    val selectedOption: String = "",
    val hasAnswered: Boolean = false,
    val isAnswerCorrect: Boolean = false,
    val isGameOver: Boolean = false
)
