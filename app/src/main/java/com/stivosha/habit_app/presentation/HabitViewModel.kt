package com.stivosha.habit_app.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.habit_app.presentation.model.Habit
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {

    private val _items = mutableStateListOf<Habit>()
    val items: List<Habit> = _items

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            _items.add(habit)
        }
    }

    fun getById(id: Long?): Habit? {
        if (id == null) return null
        return items.find { it.id == id }
    }

    fun editHabit(habit: Habit) {
        viewModelScope.launch {
            val index = items.indexOfFirst { it.id == habit.id }
            _items[index] = habit
        }
    }
}