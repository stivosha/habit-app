package com.stivosha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stivosha.data.remote.HabitRemoteEntity
import com.stivosha.domain.Habit
import com.stivosha.domain.Priority.HIGH
import com.stivosha.domain.Priority.LOW
import com.stivosha.domain.Priority.MEDIUM
import com.stivosha.domain.Type.BAD
import com.stivosha.domain.Type.GOOD

@Entity(tableName = "habits")
data class HabitDbEntity(
    @PrimaryKey
    val uid: String,
    val name: String = "",
    val description: String = "",
    val times: Int? = null,
    val period: Int? = null,
    val date: Int = 0,
    val priority: Int = 0,
    val type: Int = 0,
    val color: Long = 0xFFFF0000
)

fun Habit.toDbEntity() = HabitDbEntity(
    uid = this.uid,
    name = this.name,
    description = this.description,
    times = 0,
    period = 0,
    date = this.date + 1,
    priority = when (this.priority) {
        LOW -> 0
        MEDIUM -> 1
        HIGH -> 2
    },
    type = when (this.type) {
        GOOD -> 0
        BAD -> 1
    },
    color = this.color,
)

fun HabitDbEntity.toHabit() = Habit(
    uid = this.uid,
    name = this.name,
    description = this.description,
    times = 0,
    period = 0,
    date = this.date + 1,
    priority = when (this.priority) {
        0 -> LOW
        1 -> MEDIUM
        2 -> HIGH
        else -> throw IllegalArgumentException("No priority with priority=${this.priority}")
    },
    type = when (this.type) {
        0 -> GOOD
        1 -> BAD
        else -> throw IllegalArgumentException("No type with type=${this.type}")
    },
    color = this.color,
)