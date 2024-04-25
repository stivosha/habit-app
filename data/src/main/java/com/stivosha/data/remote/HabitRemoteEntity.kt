package com.stivosha.data.remote

import com.stivosha.data.db.HabitDbEntity
import com.stivosha.data.db.toDbEntity
import com.stivosha.data.db.toHabit
import com.stivosha.domain.Habit
import com.stivosha.domain.Priority
import com.stivosha.domain.Type

data class HabitRemoteEntity(
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

fun Habit.toRemoteEntity(isNew: Boolean) = HabitRemoteEntity(
    uid = if (isNew) null else this.uid,
    title = this.name,
    color = this.color,
    count = this.times ?: 0,
    date = this.date,
    description = this.description,
    frequency = 0,
    priority = when (this.priority) {
        Priority.LOW -> 0
        Priority.MEDIUM -> 1
        Priority.HIGH -> 2
    },
    type = when (this.type) {
        Type.GOOD -> 0
        Type.BAD -> 1
    }
)

fun HabitRemoteEntity.toHabit() = Habit(
    uid = this.uid ?: "temp",                       // Случай ?: "temp" невозможен
    name = this.title,                              // 2 модели можно завести но лень
    date = this.date,
    times = this.count,
    period = this.frequency,
    description = this.description,
    priority = when (this.priority) {
        0 -> Priority.LOW
        1 -> Priority.MEDIUM
        2 -> Priority.HIGH
        else -> throw IllegalArgumentException("No priority with priority=${this.priority}")
    },
    type = when (this.type) {
        0 -> Type.GOOD
        1 -> Type.BAD
        else -> throw IllegalArgumentException("No type with type=${this.type}")
    },
    color = this.color
)