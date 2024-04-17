package com.stivosha.habit_app.presentation

import com.stivosha.habit_app.presentation.model.Habit

sealed class HabitsState {
    data object Loading : HabitsState()
    data class Data(
        val habits: List<Habit>? = null,
        val errorText: String? = null
    ) : HabitsState()
}