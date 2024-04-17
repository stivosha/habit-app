package com.stivosha.habit_app.data

import com.stivosha.habit_app.data.db.HabitDao
import com.stivosha.habit_app.data.remote.HabitApi
import com.stivosha.habit_app.data.remote.toHabit
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.toHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach


class HabitRepositoryImpl(
    private val dao: HabitDao,
    private val api: HabitApi
) {
    fun fetchHabits() = flow {
        emit(api.getHabits().map { it.toHabit() })
    }

    suspend fun saveAll(habits: List<Habit>) {
        dao.deleteAll()
        dao.insertAll(*habits.toTypedArray())
    }

    fun observeHabits(): Flow<List<Habit>> {
        return dao.getAll()
    }

    suspend fun insert(habit: Habit) = flow<Unit> {
        api.putHabit(habit.toHabit(isNew = true))
    }.onEach { dao.insert(habit) }

    fun edit(habit: Habit) = flow<Unit> {
        val g = habit.toHabit(isNew = false)
        api.putHabit(g)
    }.onEach { dao.update(habit) }

    suspend fun getByUid(uid: String): Habit = dao.getById(uid)
}