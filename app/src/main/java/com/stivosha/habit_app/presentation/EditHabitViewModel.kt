package com.stivosha.habit_app.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.habit_app.App
import com.stivosha.habit_app.data.HabitRepositoryImpl
import com.stivosha.habit_app.presentation.model.Habit
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

class EditHabitViewModel : ViewModel() {

    private var repository: HabitRepositoryImpl = App.repository

    suspend fun addHabit(habit: Habit) = repository.insert(habit)

    fun editHabit(habit: Habit) = repository.edit(habit)

    suspend fun getHabitById(id: String?): Habit? {
        if (id == null) return null
        return repository.getByUid(id)
    }
}