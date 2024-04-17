package com.stivosha.habit_app.data.remote

import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Priority.HIGH
import com.stivosha.habit_app.presentation.model.Priority.LOW
import com.stivosha.habit_app.presentation.model.Priority.MEDIUM
import com.stivosha.habit_app.presentation.model.Type.BAD
import com.stivosha.habit_app.presentation.model.Type.GOOD

data class HabitEntity(
    val uid: String?,
    val color: Long,
    val count: Int,
    val date: Int,
    val description: String,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int
)

fun HabitEntity.toHabit() = Habit(
    uid = this.uid ?: "temp",
    name = this.title,
    date = this.date,
    description = this.description,
    priority = when (this.priority) {
        0 -> LOW
        1 -> MEDIUM
        2 -> HIGH
        else -> LOW
    },
    type = when (this.type) {
        0 -> GOOD
        1 -> BAD
        else -> BAD
    },
    color = this.color
)