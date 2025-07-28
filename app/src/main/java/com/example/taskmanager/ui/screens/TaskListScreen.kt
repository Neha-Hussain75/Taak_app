package com.example.taskmanager.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskStatus
import com.example.taskmanager.ui.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onAddClick: () -> Unit,
    onEditClick: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("All Tasks", "Pending", "Done")

    Scaffold(
        containerColor = Color(0xFFF6F6F8),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "📋",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(end = 4.dp, top = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "My Tasks",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                },
                actions = {
                    OutlinedButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .padding(end = 16.dp, top = 20.dp)
                            .height(40.dp)
                            .widthIn(min = 96.dp),
                        border = BorderStroke(1.dp, Color(0xFF939396)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFF6F6F8),
                            contentColor = Color(0xFF414142)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Task",
                            tint = Color(0xFF414142),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add Task",
                            fontSize = 13.sp,
                            color = Color(0xFF414142)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD1E0EE)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding() + 2.dp,
                    bottom = 16.dp
                )
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFFD1E0EE),
                contentColor = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) Color(0xC9756D6D) else Color.Gray
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FF))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                val filteredTasks = when (selectedTab) {
                    1 -> tasks.filter { it.status == TaskStatus.PENDING }
                    2 -> tasks.filter { it.status == TaskStatus.DONE }
                    else -> tasks
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredTasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No tasks found.")
                    }
                } else {
                    Column {
                        filteredTasks.forEach { task ->
                            TaskCard(
                                task = task,
                                onEditClick = { onEditClick(task) },
                                onDeleteClick = { onDeleteClick(task) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}
