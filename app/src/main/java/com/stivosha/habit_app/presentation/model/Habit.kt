package com.stivosha.habit_app.presentation.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stivosha.habit_app.presentation.model.Priority.LOW
import com.stivosha.habit_app.presentation.model.Type.GOOD
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Entity(tableName = "habits")
@Parcelize
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = Random.nextLong(),
    val name: String = "",
    val description: String = "",
    val times: Int? = null,
    val period: Int? = null,
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