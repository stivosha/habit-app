package com.stivosha.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitDbEntity::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}