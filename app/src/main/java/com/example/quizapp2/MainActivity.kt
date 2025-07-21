package com.example.quizapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quizapp2.navigation.NavGraph
import com.example.quizapp2.ui.theme.QuizApp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizApp2Theme {
                NavGraph()
            }
        }
    }
}
