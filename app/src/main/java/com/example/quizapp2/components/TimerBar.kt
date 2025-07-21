//package com.example.quizapp2.components
//
//class TimerBar {
//}
package com.example.quizapp2.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerBar(totalTime: Int = 10, onTimeOut: () -> Unit) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        onTimeOut()
    }

    LinearProgressIndicator(
        progress = timeLeft / totalTime.toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(8.dp)
    )
}
