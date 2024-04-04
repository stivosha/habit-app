package com.stivosha.habit_app.data.filter

import com.stivosha.habit_app.presentation.model.Habit

fun interface HabitFilter {
    fun invoke(habit: Habit): Boolean
}