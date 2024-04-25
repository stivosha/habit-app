package com.stivosha.data

import com.stivosha.data.db.HabitDao
import com.stivosha.data.db.toDbEntity
import com.stivosha.data.db.toHabit
import com.stivosha.data.remote.HabitApi
import com.stivosha.data.remote.toHabit
import com.stivosha.data.remote.toRemoteEntity
import com.stivosha.domain.Habit
import com.stivosha.domain.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class HabitRepositoryImpl(
    private val dao: HabitDao,
    private val api: HabitApi
) : HabitRepository {
    override fun loadHabits() = flow {
        emit(api.getHabits().map { it.toHabit() })
    }

    override fun observeHabits(): Flow<List<Habit>> {
        return dao.getAll().map { it.map { it.toHabit() } }
    }

    override suspend fun saveAll(habits: List<Habit>) {
        dao.deleteAll()
        val habitsEntity = habits.map { it.toDbEntity() }
        dao.insertAll(*habitsEntity.toTypedArray())
    }

    override fun insert(habit: Habit) = flow<Unit> {
        api.putHabit(habit.toRemoteEntity(isNew = true))
    }.onEach { dao.insert(habit.toDbEntity()) }

    override fun edit(habit: Habit) = flow<Unit> {
        api.putHabit(habit.toRemoteEntity(isNew = false))
    }.onEach { dao.update(habit.toDbEntity()) }

    override suspend fun getByUid(uid: String): Habit = dao.getById(uid).toHabit()
}