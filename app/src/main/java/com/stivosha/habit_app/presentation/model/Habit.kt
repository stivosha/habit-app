package com.stivosha.habit_app.presentation.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stivosha.habit_app.data.remote.HabitEntity
import com.stivosha.habit_app.presentation.model.Priority.*
import com.stivosha.habit_app.presentation.model.Type.*
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Entity(tableName = "habits")
@Parcelize
data class Habit(
    @PrimaryKey
    val uid: String,
    val name: String = "",
    val description: String = "",
    val times: Int? = null,
    val period: Int? = null,
    val date: Int = 0,
    val priority: Priority = LOW,
    val type: Type = GOOD,
    val color: Long = 0xFFFF0000
) : Parcelable

enum class Priority {
    HIGH, MEDIUM, LOW
}

enum class Type {
    GOOD, BAD
}

fun Habit.toHabit(isNew: Boolean) = HabitEntity(
    uid = if (isNew) null else this.uid,
    color = this.color,
    count = 0,
    date = this.date + 1,
    frequency = this.period ?: 0,
    description = this.description,
    title = this.name,
    priority = when (this.priority) {
        HIGH -> 2
        MEDIUM -> 1
        LOW -> 0
    },
    type = when (this.type) {
        GOOD -> 0
        BAD -> 1
    }
)