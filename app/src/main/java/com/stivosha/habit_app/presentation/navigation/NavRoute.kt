package com.stivosha.habit_app.presentation.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Priority

sealed class NavRoute(val path: String) {
    data object HabitsList : NavRoute("habit")
    data object AddHabit : NavRoute("add_habit")
    data object EditHabit : NavRoute("edit_habit?habitId={habitId}") {
        fun createRoute(habit: Habit): String {
            val builder = StringBuilder("$path?")
            builder.append("name=${habit.name}&")
            builder.append("description=${habit.description}&")
            builder.append("priority=${habit.priority}&")
            builder.append("type=${habit.type}&")
            builder.append("color=${habit.color}")
            return builder.toString()
        }

        fun fromArguments(args: Bundle?): Habit? {
            if (args == null) return null
            val n = args.getString("name")
            return Habit(
                name = args.getString("name") ?: return null,
                description = args.getString("description") ?: return null,
                priority = Priority.LOW//args.getString("priority") ?: return null,
            )
        }
    }
}