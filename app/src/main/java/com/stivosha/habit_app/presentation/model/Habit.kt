package com.stivosha.habit_app.presentation.model

import android.os.Parcelable
import com.stivosha.habit_app.presentation.model.Priority.LOW
import com.stivosha.habit_app.presentation.model.Type.GOOD
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Habit(
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