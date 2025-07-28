package com.example.taskmanager.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromPriority(priority: TaskPriority): String = priority.name

    @TypeConverter
    fun toPriority(priority: String): TaskPriority = TaskPriority.valueOf(priority)

    @TypeConverter
    fun fromStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toStatus(status: String): TaskStatus = TaskStatus.valueOf(status)
}
