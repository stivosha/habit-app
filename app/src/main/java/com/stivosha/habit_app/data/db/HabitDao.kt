package com.stivosha.habit_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stivosha.habit_app.presentation.model.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    suspend fun getAll(): List<Habit>

    @Query("SELECT * FROM habits WHERE id=:id")
    suspend fun getById(id: Long): Habit

    @Insert
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)
}