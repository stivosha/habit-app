package com.stivosha.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun loadHabits(): Flow<List<Habit>>
    fun observeHabits(): Flow<List<Habit>>
    suspend fun saveAll(habits: List<Habit>)
    fun insert(habit: Habit): Flow<Unit>
    fun edit(habit: Habit): Flow<Unit>
    suspend fun getByUid(uid: String): Habit
}