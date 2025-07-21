package com.example.quizapp2.data

import com.example.quizapp2.model.Question

object QuestionData {
    val questionList = listOf(
        Question(
            id = 1,
            questionText = "What is the capital of Pakistan?",
            options = listOf("Lahore", "Islamabad", "Karachi", "Peshawar"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 2,
            questionText = "Which planet is known as the Red Planet?",
            options = listOf("Earth", "Venus", "Mars", "Saturn"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 3,
            questionText = "Who wrote Hamlet?",
            options = listOf("Shakespeare", "Wordsworth", "Chaucer", "Milton"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 4,
            questionText = "Fastest land animal?",
            options = listOf("Lion", "Tiger", "Horse", "Cheetah"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 5,
            questionText = "What is H2O?",
            options = listOf("Salt", "Water", "Oxygen", "Hydrogen"),
            correctAnswerIndex = 1
        )
    )
}