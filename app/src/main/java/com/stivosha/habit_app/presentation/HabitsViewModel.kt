package com.stivosha.habit_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.habit_app.data.HabitData
import com.stivosha.habit_app.data.filter.FilterByName
import com.stivosha.habit_app.data.filter.HabitFilter
import com.stivosha.habit_app.presentation.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {

    private val _filters = mutableSetOf<HabitFilter>()
    private val _habits = MutableStateFlow(HabitData.getHabits())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            _habits.value = HabitData
                .getHabits()
                .filter { habit ->
                    _filters.isEmpty() || _filters.any { filter -> filter.invoke(habit) }
                }
        }
    }

    fun addFilter(name: String) {
        val filter = FilterByName(name)
        _filters.remove(filter)
        _filters.add(filter)
        fetchData()
    }
}