package com.stivosha.habit_app.data.filter

import com.stivosha.domain.Habit

class FilterByName(
    private val name: String
) : HabitFilter {
    override fun invoke(habit: Habit): Boolean = habit.name.contains(name)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}