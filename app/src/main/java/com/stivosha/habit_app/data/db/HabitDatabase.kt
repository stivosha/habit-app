package com.stivosha.habit_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stivosha.habit_app.presentation.model.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}