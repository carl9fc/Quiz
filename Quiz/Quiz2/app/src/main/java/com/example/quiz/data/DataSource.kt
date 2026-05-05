package com.example.quiz.data

import com.example.quiz.model.Question

object DataSource {
    val questions = listOf(
        Question(
            text = "¿Cuál es el lenguaje oficial para el desarrollo nativo en Android recomendado por Google?",
            options = listOf("Java", "Kotlin", "Swift", "C#"),
            correctAnswer = "Kotlin"
        ),
        Question(
            text = "¿Qué componente de la arquitectura MVVM se encarga de la lógica de negocio y preparación de datos para la UI?",
            options = listOf("Model", "View", "ViewModel", "Data Source"),
            correctAnswer = "ViewModel"
        ),
        Question(
            text = "¿Qué herramienta se utiliza para definir la interfaz de usuario de forma declarativa en Android?",
            options = listOf("XML", "Jetpack Compose", "React Native", "Flutter"),
            correctAnswer = "Jetpack Compose"
        ),
        Question(
            text = "¿Cuál es el StateFlow que permite emitir un único valor inicial y actualizaciones posteriores?",
            options = listOf("LiveData", "SharedFlow", "StateFlow", "Flow"),
            correctAnswer = "StateFlow"
        ),
        Question(
            text = "¿Qué anotación se usa para marcar una función Composable?",
            options = listOf("@Component", "@Composable", "@Preview", "@Layout"),
            correctAnswer = "@Composable"
        )
    )
}
