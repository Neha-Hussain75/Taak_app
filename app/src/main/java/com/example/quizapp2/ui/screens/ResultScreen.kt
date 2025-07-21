package com.example.quizapp2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp2.navigation.Screen
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.quizapp2.R

@Composable
fun ResultScreen(score: Int, navController: NavController) {
    val percentage = (score / 5f) * 100
    val lato = FontFamily(Font(R.font.lato_bold_italic))
    val (message, messageColor) = when {
        percentage >= 80 -> "  Excellent!" to Color(0xFFA19E9E)     // Green
        percentage >= 60 -> " Great job!" to Color(0xFFA19E9E)      // Blue
        percentage >= 40 -> " Keep practicing!" to Color(0xFFA19E9E) // Amber
        else -> " Better luck next time!" to Color(0xFFA19E9E)     // Red
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF3E5F5), Color(0xFFFCE4EC))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = "🎉 Quiz Completed!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6A1B9A)
            )

            Text(
                text = "Your Score: $score / 5",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            // Color-based message below score
//            Text(
//                text = message,
//                fontSize = 18.sp,
//                color = messageColor,
//                fontFamily = lato
//            )

            OutlinedButton(
                onClick = { navController.navigate(Screen.Home.route) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home", fontSize = 18.sp, color = Color.White,fontFamily = lato)
            }


        }
    }
}