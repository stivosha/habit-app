package com.stivosha.habit_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stivosha.habit_app.presentation.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE uid=:uid")
    suspend fun getById(uid: String): Habit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg habits: Habit)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Update
    suspend fun update(habit: Habit)
}