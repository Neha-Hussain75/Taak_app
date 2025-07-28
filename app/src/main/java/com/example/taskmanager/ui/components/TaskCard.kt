package com.example.taskmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskPriority
import com.example.taskmanager.data.TaskStatus

@Composable
fun TaskCard(
    task: Task,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F8) // light blue color for the whole card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        task.description.take(40) + if (task.description.length > 40) "..." else "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Priority Tag
                    Text(
                        text = task.priority.name,
                        color = Color.White,
                        modifier = Modifier
                            .background(getPriorityColor(task.priority), shape = MaterialTheme.shapes.small)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier
                            .background(Color(0xFFF6F6F8))
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Edit")
                            },
                            onClick = {
                                menuExpanded = false
                                onEditClick()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text("Delete", color = Color.Black)
                            },
                            onClick = {
                                menuExpanded = false
                                onDeleteClick()
                            }
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (task.status == TaskStatus.DONE) "✔ Done" else "⏳ Pending",
                color = if (task.status == TaskStatus.DONE) Color(0xFF3D3D3B) else Color(0xFF3D3D3B),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.HIGH -> Color(0xFFAF4343)
        TaskPriority.MEDIUM -> Color(0xFFD9BA5E) // Yellow
        TaskPriority.LOW -> Color(0xFF87D289)    // Green
    }
}