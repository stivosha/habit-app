package com.stivosha.habit_app.data.filter

import com.stivosha.domain.Habit

fun interface HabitFilter {
    fun invoke(habit: Habit): Boolean
}