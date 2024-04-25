package com.stivosha.habit_app.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stivosha.domain.HabitRepository
import com.stivosha.habit_app.data.filter.FilterByName
import com.stivosha.habit_app.data.filter.HabitFilter
import com.stivosha.habit_app.presentation.HabitsState.Data
import com.stivosha.habit_app.presentation.HabitsState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

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
            .loadHabits()
            .onEach {
                repository.saveAll(it)
            }
            .catch {
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