package com.example.quizapp2.ui.screens

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp2.data.QuestionData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    onQuizComplete: (score: Int) -> Unit
) {
    val questions = QuestionData.questionList
    var questionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(10) }
    var timerFinished by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentQuestion = questions[questionIndex]

    LaunchedEffect(questionIndex) {
        timer = 10
        timerFinished = false
        selectedOptionIndex = -1
        answered = false
        while (timer > 0) {
            delay(1000)
            timer--
        }
        if (!answered) {
            answered = true
            timerFinished = true
            selectedOptionIndex = -1 // No selection, highlight correct answer
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            MediaPlayer.create(context, notification).start()

            coroutineScope.launch {
                delay(1200) // show correct answer briefly
                goToNextOrFinish(questionIndex, questions.size, score, onQuizComplete) {
                    questionIndex = it
                }
            }
        }

    }

    val animatedProgress by animateFloatAsState(
        targetValue = timer / 10f,
        animationSpec = tween(500),
        label = "Timer"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors =listOf(Color(0xFFFDF9FD), Color(0xFFFCF4F6))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Header Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFFF3E5F5),
                border = BorderStroke(1.dp, Color(0xFFCE93D8)),
                shadowElevation = 4.dp,
                tonalElevation = 6.dp
            ) {
                Text(
                    text = "📘 Question ${questionIndex + 1} / ${questions.size}",
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = Color(0xFFB544CB),
                    letterSpacing = 0.3.sp
                )
            }

val timerTextColor = if(timer <= 3) Color(0xFFD53F3F) else Color.White
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFFF3E5F5),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = "⏰ $timer s",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Medium,
                    color = timerTextColor,
                    fontSize = 14.sp
                )
            }
        }

        // --- Timer Bar ---
        LinearProgressIndicator(
            progress = animatedProgress,
            color = Color(0xFAE1CBE5),
            trackColor = Color(0xFFEDE7F6),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Question Card ---
        Card(
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFCE93D8)) ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "General Knowledge",
                    color = Color(0xFFEDE7F6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentQuestion.questionText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Options ---
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            currentQuestion.options.forEachIndexed { index, option ->
                val isCorrect = index == currentQuestion.correctAnswerIndex
                val isWrong = index == selectedOptionIndex && !isCorrect

                val borderColor = when {
                    answered && isCorrect -> Color(0xFF77CB7B)
                    answered && isWrong -> Color(0xFFE57D7A)
                    else -> Color.Transparent
                }

                Card(
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(2.dp, borderColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !answered) {
                            selectedOptionIndex = index
                            answered = true
                            if (isCorrect) score++
                            coroutineScope.launch {
                                delay(800)
                                goToNextOrFinish(questionIndex, questions.size, score, onQuizComplete) {
                                    questionIndex = it
                                }
                            }
                        }
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(option, fontSize = 16.sp)
                    }
                }
            }
        }

        // --- Time’s up message ---
        if (timerFinished) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "⏰ Time’s up!",
                color = Color(0xFFCC5959),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

private fun goToNextOrFinish(
    currentIndex: Int,
    totalQuestions: Int,
    score: Int,
    onQuizComplete: (Int) -> Unit,
    updateIndex: (Int) -> Unit
) {
    if (currentIndex + 1 >= totalQuestions) {
        onQuizComplete(score)
    } else {
        updateIndex(currentIndex + 1)
    }
}
