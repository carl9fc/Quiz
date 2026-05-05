<img width="1080" height="2408" alt="image" src="https://github.com/user-attachments/assets/817789b3-c1b1-401f-bba4-cd5115d139d0" />
<img width="717" height="1600" alt="image" src="https://github.com/user-attachments/assets/40028e10-e367-45de-b9e7-46134ce19d44" />
<img width="717" height="1600" alt="image" src="https://github.com/user-attachments/assets/5fe048d3-94fd-4b1f-8229-efbeb8606905" />

¿Dónde está el ViewModel y qué responsabilidades tiene?
El ViewModel se encuentra en la clase QuizViewModel dentro del paquete com.example.quiz.ui.
Sus responsabilidades principales son:

Gestión del Estado: Almacena y expone el estado de la interfaz (QuizUiState) a través de un StateFlow.

Lógica de Negocio: Contiene toda la lógica del quiz (validar si una respuesta es correcta, calcular el puntaje acumulado y determinar cuándo termina el juego).

Persistencia: Al extender de ViewModel, garantiza que el progreso del estudiante no se pierda ante cambios de configuración, como la rotación de la pantalla.

Procesamiento de Eventos: Actúa como el único punto de entrada para las acciones del usuario enviadas desde la UI.

¿Cómo definiste el QuizUiState y por qué es inmutable?
El QuizUiState se definió como una data class en com.example.quiz.ui utilizando propiedades declaradas con val.
Es inmutable por las siguientes razones:

Predictibilidad: Garantiza que el estado no pueda ser modificado accidentalmente desde fuera del ViewModel (por ejemplo, directamente desde un Composable).

Consistencia: Para actualizar la UI, el ViewModel debe emitir una copia completa del estado (copy()), lo que asegura que cada versión del estado sea consistente y atómica.

Optimización en Compose: Jetpack Compose puede comparar fácilmente si el estado ha cambiado para decidir si debe recomponer o no un componente, mejorando el rendimiento.

3. ¿Cómo se evidencia el flujo de datos unidireccional en tu solución?  
El flujo unidireccional (UDF) se evidencia en el ciclo cerrado de comunicación:

El Estado baja (State flows DOWN): El QuizViewModel expone el uiState. La función QuizScreen lo observa mediante collectAsState() y pasa los valores necesarios hacia abajo a los componentes hijos (como el texto de la pregunta o el puntaje).

Los Eventos suben (Events flow UP): Cuando el usuario interactúa (por ejemplo, toca una opción en QuestionCard), el componente no cambia ninguna variable local. En su lugar, llama a una función lambda que sube el evento hasta el ViewModel (quizViewModel.selectOption(it)).
Actualización: El ViewModel procesa el evento, genera un nuevo estado inmutable y lo emite. Compose detecta esta nueva emisión y refresca la pantalla automáticamente.

