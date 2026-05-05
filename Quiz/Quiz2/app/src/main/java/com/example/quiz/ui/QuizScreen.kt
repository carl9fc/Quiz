package com.example.quiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel = viewModel()
) {
    val uiState by quizViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quiz Educativo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        QuizStatusHeader(
            currentQuestionIndex = uiState.currentQuestionIndex,
            totalQuestions = uiState.totalQuestions,
            score = uiState.score
        )

        QuestionCard(
            question = uiState.currentQuestion.text,
            options = uiState.currentQuestion.options,
            selectedOption = uiState.selectedOption,
            onOptionSelected = { quizViewModel.selectOption(it) },
            enabled = !uiState.hasAnswered
        )

        if (uiState.hasAnswered) {
            AnswerFeedback(isCorrect = uiState.isAnswerCorrect)
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionButtons(
            hasAnswered = uiState.hasAnswered,
            selectedOption = uiState.selectedOption,
            onCheckAnswer = { quizViewModel.checkAnswer() },
            onNextQuestion = { quizViewModel.goToNextQuestion() }
        )
    }

    if (uiState.isGameOver) {
        GameOverDialog(
            score = uiState.score,
            totalQuestions = uiState.totalQuestions,
            onRestart = { quizViewModel.restartGame() }
        )
    }
}

@Composable
fun QuizStatusHeader(
    currentQuestionIndex: Int,
    totalQuestions: Int,
    score: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Pregunta ${currentQuestionIndex + 1} de $totalQuestions")
        Text(text = "Puntaje: $score")
    }
}

@Composable
fun QuestionCard(
    question: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    enabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(Modifier.selectableGroup()) {
                options.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) },
                                enabled = enabled,
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null,
                            enabled = enabled
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerFeedback(isCorrect: Boolean) {
    val color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
    val text = if (isCorrect) "¡Correcto!" else "Incorrecto"
    
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ActionButtons(
    hasAnswered: Boolean,
    selectedOption: String,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit
) {
    if (!hasAnswered) {
        Button(
            onClick = onCheckAnswer,
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedOption.isNotEmpty()
        ) {
            Text("Validar respuesta")
        }
    } else {
        OutlinedButton(
            onClick = onNextQuestion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}

@Composable
fun GameOverDialog(
    score: Int,
    totalQuestions: Int,
    onRestart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("¡Quiz terminado!") },
        text = { Text("Tu puntaje final es $score de $totalQuestions") },
        confirmButton = {
            TextButton(onClick = onRestart) {
                Text("Reiniciar")
            }
        }
    )
}
