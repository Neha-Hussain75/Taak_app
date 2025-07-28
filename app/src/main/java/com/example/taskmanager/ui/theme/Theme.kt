package com.example.taskmanager.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Custom Color Palette
val LightPurple = Color.White      // Light background
val MediumPurple = Color(0xFFD1C4E9)     // Cards, surfaces
val DarkPurple = Color(0xFF9575CD)       // Primary elements
val ButtonPurple = Color(0xFFB39DDB)     // Buttons or highlights

// Light Color Scheme
private val LightColors = lightColorScheme(
    primary = DarkPurple,
    onPrimary = Color.White,
    secondary = ButtonPurple,
    onSecondary = Color.White,
    background = LightPurple,
    surface = MediumPurple,
    onSurface = Color.Black
)

// Theme Wrapper
@Composable
fun QuizApp2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
