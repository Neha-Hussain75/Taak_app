package com.example.taskmanager.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskPriority
import com.example.taskmanager.data.TaskStatus
import com.example.taskmanager.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import androidx.compose.ui.graphics.SolidColor
import java.util.*
import androidx.compose.ui.Alignment
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskViewModel: TaskViewModel,
    existingTask: Task? = null,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    var title by remember { mutableStateOf(TextFieldValue(existingTask?.title ?: "")) }
    var description by remember { mutableStateOf(TextFieldValue(existingTask?.description ?: "")) }
    var date by remember { mutableStateOf(existingTask?.date ?: currentDate) }
    var priority by remember { mutableStateOf(existingTask?.priority?.name ?: "Medium") }
    var status by remember { mutableStateOf(existingTask?.status?.name ?: "Pending") }

    val priorities = listOf("Low", "Medium", "High")
    val statuses = listOf("Pending", "Done")

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text(if (existingTask != null) "Edit Task" else "Add New Task") },
                navigationIcon = {
                    IconButton(onClick = { onCancel() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD1E0EE), // Yellow
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            val showDatePicker = remember { mutableStateOf(false) }

            if (showDatePicker.value) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(context, { _, y, m, d ->
                    date =
                        "$y-${(m + 1).toString().padStart(2, '0')}-${d.toString().padStart(2, '0')}"
                }, year, month, day).show()

                showDatePicker.value = false
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker.value = true }) {
                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    enabled = false, // disables typing and keyboard
                    label = { Text("Date") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black
                    )
                )
            }

            // 🔻 Dropdowns side by side
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                var priorityExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = priorityExpanded,
                    onExpandedChange = { priorityExpanded = !priorityExpanded }

                ) {
                    OutlinedTextField(
                        value = priority,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Priority") },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )
                    DropdownMenu(
                        expanded = priorityExpanded,
                        onDismissRequest = { priorityExpanded = false },
                        modifier = Modifier
                            .background(Color(0xFFF6F6F8))
                    ) {
                        priorities.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    priority = it
                                    priorityExpanded = false
                                }
                            )
                        }
                    }
                }

                var statusExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    OutlinedTextField(
                        value = status,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )
                    DropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false },
                        modifier = Modifier
                            .background(Color(0xFFF6F6F8))
                    ) {
                        statuses.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    status = it
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 🔘 Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = onCancel,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFD1E0EE),
                        contentColor = Color(0xFF414142)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(Color.Black))
                ) {
                    Text("Cancel")
                }

                // If editing existing task, show Delete button in middle
                if (existingTask != null) {
                    OutlinedButton(
                        onClick = {
                            taskViewModel.deleteTask(existingTask)
                            onSave()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Delete")
                    }
                }

                // Save Button
                OutlinedButton(
                    onClick = {
                        val task = Task(
                            id = existingTask?.id ?: 0,
                            title = title.text,
                            description = description.text,
                            date = date,
                            priority = TaskPriority.valueOf(priority.uppercase()),
                            status = TaskStatus.valueOf(status.uppercase())
                        )
                        if (existingTask == null)
                            taskViewModel.addTask(task)
                        else
                            taskViewModel.updateTask(task)

                        onSave()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFD1E0EE),
                        contentColor = Color(0xFF414142)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(Color.Black))
                ) {
                    Text(" Save ")
                }
            }
        }
    }
}
