package com.example.quiz.ui

import androidx.lifecycle.ViewModel
import com.example.quiz.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun selectOption(option: String) {
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun checkAnswer() {
        val currentState = _uiState.value
        val isCorrect = currentState.selectedOption == currentState.currentQuestion.correctAnswer
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        
        _uiState.update { 
            it.copy(
                hasAnswered = true,
                isAnswerCorrect = isCorrect,
                score = newScore
            )
        }
    }

    fun goToNextQuestion() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1
        if (nextIndex < DataSource.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    currentQuestion = DataSource.questions[nextIndex],
                    selectedOption = "",
                    hasAnswered = false,
                    isAnswerCorrect = false
                )
            }
        } else {
            _uiState.update { it.copy(isGameOver = true) }
        }
    }

    fun restartGame() {
        _uiState.value = QuizUiState()
    }
}
