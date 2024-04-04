package com.stivosha.habit_app.presentation

import androidx.lifecycle.ViewModel
import com.stivosha.habit_app.data.HabitData
import com.stivosha.habit_app.presentation.model.Habit

class EditHabitViewModel : ViewModel() {

    fun addHabit(habit: Habit) {
        HabitData.addHabit(habit)
    }

    fun editHabit(id:Long?, habit: Habit) {
        if(id == null) return
        HabitData.editHabit(id, habit)
    }

    fun getHabitById(id: Long?): Habit? {
        if(id == null) return null
        return HabitData.getHabitById(id)
    }
}