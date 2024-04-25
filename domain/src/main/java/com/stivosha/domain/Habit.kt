package com.stivosha.domain

import com.stivosha.domain.Priority.LOW
import com.stivosha.domain.Type.GOOD

data class Habit(
    val uid: String,
    val name: String = "",
    val description: String = "",
    val times: Int? = null,
    val period: Int? = null,
    val date: Int = 0,
    val priority: Priority = LOW,
    val type: Type = GOOD,
    val color: Long = 0xFFFF0000
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class Type {
    GOOD, BAD
}