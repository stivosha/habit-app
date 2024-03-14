package com.stivosha.habit_app.presentation.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Priority

sealed class NavRoute(val path: String) {
    data object HabitsList : NavRoute("habit")
    data object AddHabit : NavRoute("add_habit")
    data object EditHabit : NavRoute("edit_habit/{habitId}") {
        fun createRoute(habit: Habit): String {
            return "edit_habit/${habit.id}"
        }
    }
}