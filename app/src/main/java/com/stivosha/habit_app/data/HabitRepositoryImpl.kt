package com.stivosha.habit_app.data

import com.stivosha.habit_app.data.db.HabitDao
import com.stivosha.habit_app.presentation.model.Habit

class HabitRepositoryImpl(
    private val dao: HabitDao
) {
    suspend fun getHabits(): List<Habit> {
        return dao.getAll()
    }

    suspend fun insert(habit: Habit) {
        dao.insert(habit)
    }

    suspend fun edit(habit: Habit) {
        dao.update(habit)
    }

    suspend fun getById(id: Long): Habit = dao.getById(id)
}