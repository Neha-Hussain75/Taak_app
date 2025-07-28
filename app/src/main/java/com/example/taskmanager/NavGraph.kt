package com.example.taskmanager

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.taskmanager.data.Task
import com.example.taskmanager.ui.screens.AddEditTaskScreen
import com.example.taskmanager.ui.screens.TaskListScreen
import com.example.taskmanager.viewmodel.TaskViewModel
import com.example.taskmanager.viewmodel.TaskViewModelFactory

@Composable
fun NavGraph(startDestination: String = "task_list") {
    val navController = rememberNavController()

    // ✅ Get Application instance from LocalContext
    val application = LocalContext.current.applicationContext as Application
    val factory = TaskViewModelFactory(application)
    val taskViewModel: TaskViewModel = viewModel(factory = factory)

    var selectedTask by remember { mutableStateOf<Task?>(null) }

    // ✅ Observe tasks
    val tasks by taskViewModel.allTasks.collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = startDestination) {

        // ✅ Task List Screen (Home)
        composable("task_list") {
            TaskListScreen(
                tasks = tasks,
                onAddClick = {
                    selectedTask = null
                    navController.navigate("add_edit_task")
                },
                onEditClick = { task ->
                    selectedTask = task
                    navController.navigate("add_edit_task")
                },
                onDeleteClick = { task ->
                    taskViewModel.deleteTask(task)
                },

            )
        }

        // ✅ Add or Edit Task Screen (Bottom Sheet style UI)
        composable("add_edit_task") {
            AddEditTaskScreen(
                taskViewModel = taskViewModel,
                existingTask = selectedTask,
                onSave = {
                    selectedTask = null
                    navController.popBackStack()
                },
                onCancel = {
                    selectedTask = null
                    navController.popBackStack()
                }
            )
        }
    }
}
