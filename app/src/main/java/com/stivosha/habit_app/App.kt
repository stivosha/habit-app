package com.stivosha.habit_app

import android.app.Application
import androidx.room.Room
import com.stivosha.habit_app.data.HabitRepositoryImpl
import com.stivosha.habit_app.data.db.HabitDatabase

class App : Application() {

    companion object {
        lateinit var repository: HabitRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java, "habit-database"
        ).build()
        repository = HabitRepositoryImpl(db.habitDao())
    }
}