package com.stivosha.habit_app.presentation

import com.stivosha.domain.Habit

sealed class HabitsState {
    data object Loading : HabitsState()
    data class Data(
        val habits: List<Habit>? = null,
        val errorText: String? = null
    ) : HabitsState()
}