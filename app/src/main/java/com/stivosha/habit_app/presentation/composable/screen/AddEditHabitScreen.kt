package com.stivosha.habit_app.presentation.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.EditHabitViewModel
import com.stivosha.habit_app.presentation.composable.components.AddHabitElevatedCard
import com.stivosha.habit_app.presentation.composable.components.ColorPickerGroup
import com.stivosha.habit_app.presentation.composable.components.PeriodicityTextField
import com.stivosha.habit_app.presentation.composable.components.PriorityExposedDropdown
import com.stivosha.habit_app.presentation.composable.components.TypeRadioButtonGroup
import com.stivosha.habit_app.presentation.model.Habit
import kotlinx.coroutines.launch

@Composable
fun AddEditHabitScreen(
    editHabitViewModel: EditHabitViewModel,
    closeScreen: (Habit) -> Unit,
    habitId: Long? = null
) {
    val (habit, onHabitChanged) = rememberSaveable {
        mutableStateOf(Habit())
    }
    LaunchedEffect(Unit) {
        editHabitViewModel.getHabitById(habitId)?.let {
            onHabitChanged(it)
        }
    }
    var nameFieldError by remember { mutableStateOf(false) }
    var descriptionFieldError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_name_description
        ) {
            OutlinedTextField(
                value = habit.name,
                onValueChange = { text ->
                    nameFieldError = text.isEmpty()
                    onHabitChanged(habit.copy(name = text))
                },
                supportingText = {
                    if (nameFieldError) {
                        Text(
                            text = stringResource(R.string.add_habit_title_enter_field_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(stringResource(R.string.add_habit_title_name)) },
                isError = nameFieldError
            )
            OutlinedTextField(
                value = habit.description,
                onValueChange = { text ->
                    descriptionFieldError = text.isEmpty()
                    onHabitChanged(habit.copy(description = text))
                },
                supportingText = {
                    if (descriptionFieldError) {
                        Text(
                            text = stringResource(R.string.add_habit_title_enter_field_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(stringResource(R.string.add_habit_title_description)) },
                isError = descriptionFieldError
            )
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_priority
        ) {
            PriorityExposedDropdown(habit, onHabitChanged)
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_type
        ) {
            TypeRadioButtonGroup(habit, onHabitChanged)
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_periodicity
        ) {
            PeriodicityTextField(habit, onHabitChanged)
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_color
        ) {
            ColorPickerGroup(habit, onHabitChanged)
        }
        Button(onClick = {
            nameFieldError = habit.name.isEmpty()
            descriptionFieldError = habit.description.isEmpty()
            if (!nameFieldError && !descriptionFieldError) {
                editHabitViewModel.viewModelScope.launch {
                    if (habit.id != habitId) {
                        editHabitViewModel.addHabit(habit)
                    } else {
                        editHabitViewModel.editHabit(habitId, habit)
                    }
                }
                closeScreen(habit)
            }
        }) {
            val buttonTitleRes = if (habit.id != habitId) {
                R.string.add_habit_title_button
            } else {
                R.string.edit_habit_title_button
            }
            Text(stringResource(buttonTitleRes))
        }
    }
}