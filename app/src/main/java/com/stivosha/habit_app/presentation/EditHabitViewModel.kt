package com.stivosha.habit_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.domain.Habit
import com.stivosha.domain.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {
    fun addHabit(habit: Habit) = repository.insert(habit)

    fun editHabit(habit: Habit) = repository.edit(habit)

    suspend fun getHabitById(id: String?): Habit? {
        if (id == null) return null
        return repository.getByUid(id)
    }
}