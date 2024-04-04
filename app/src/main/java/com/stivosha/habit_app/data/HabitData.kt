package com.stivosha.habit_app.data

import com.stivosha.habit_app.presentation.model.Habit

object HabitData {
    private val _habits = mutableListOf<Habit>()
    fun addHabit(habit: Habit) {
        _habits.add(habit)
    }

    fun editHabit(id: Long, habit: Habit) {
        val index = _habits.indexOfFirst { it.id == id }
        _habits[index] = habit
    }

    fun getHabits(): List<Habit> = _habits.toList()

    fun getHabitById(id: Long): Habit? = _habits.find { it.id == id }
}