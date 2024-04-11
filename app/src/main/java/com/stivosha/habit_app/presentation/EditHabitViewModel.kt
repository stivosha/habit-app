package com.stivosha.habit_app.presentation

import androidx.lifecycle.ViewModel
import com.stivosha.habit_app.App
import com.stivosha.habit_app.data.HabitData
import com.stivosha.habit_app.data.HabitRepositoryImpl
import com.stivosha.habit_app.presentation.model.Habit

class EditHabitViewModel : ViewModel() {

    private var repository: HabitRepositoryImpl = App.repository

    suspend fun addHabit(habit: Habit) {
        repository.insert(habit)
    }

    suspend fun editHabit(id:Long?, habit: Habit) {
        if(id == null) return
        repository.edit(habit)
    }

    suspend fun getHabitById(id: Long?): Habit? {
        if(id == null) return null
        return repository.getById(id)
    }
}