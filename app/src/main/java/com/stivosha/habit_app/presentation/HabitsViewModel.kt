package com.stivosha.habit_app.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.habit_app.App
import com.stivosha.habit_app.data.HabitRepositoryImpl
import com.stivosha.habit_app.data.filter.FilterByName
import com.stivosha.habit_app.data.filter.HabitFilter
import com.stivosha.habit_app.presentation.HabitsState.Data
import com.stivosha.habit_app.presentation.HabitsState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HabitsViewModel : ViewModel() {

    private var repository: HabitRepositoryImpl = App.repository
    private val _filters = mutableSetOf<HabitFilter>()
    private val _state = MutableStateFlow<HabitsState>(Loading)
    val state: StateFlow<HabitsState> = _state.asStateFlow()

    init {
        repository.observeHabits()
            .onEach {
                val data = Data(
                    habits = it.filter { habit ->
                        _filters.isEmpty() || _filters.any { filter -> filter.invoke(habit) }
                    },
                    errorText = null
                )
                _state.value = data
            }
            .launchIn(viewModelScope)
    }

    fun fetchData() {
        repository
            .fetchHabits()
            .onEach {
                repository.saveAll(it)
            }
            .catch {
                Log.d("habit-app", it.message!!)
                _state.value = Data(
                    habits = (_state.value as? Data)?.habits,
                    errorText = "Ошибка подгрузки данных"
                )
            }
            .launchIn(viewModelScope)
    }

    fun addFilter(name: String) {
        val filter = FilterByName(name)
        _filters.remove(filter)
        _filters.add(filter)
        fetchData()
    }
}