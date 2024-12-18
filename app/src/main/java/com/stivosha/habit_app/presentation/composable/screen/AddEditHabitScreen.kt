package com.stivosha.habit_app.presentation.composable.screen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stivosha.domain.Habit
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.EditHabitViewModel
import com.stivosha.habit_app.presentation.composable.components.AddHabitElevatedCard
import com.stivosha.habit_app.presentation.composable.components.ColorPickerGroup
import com.stivosha.habit_app.presentation.composable.components.PeriodicityTextField
import com.stivosha.habit_app.presentation.composable.components.PriorityExposedDropdown
import com.stivosha.habit_app.presentation.composable.components.TypeRadioButtonGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@Composable
fun AddEditHabitScreen(
    editHabitViewModel: EditHabitViewModel,
    closeScreen: (Habit) -> Unit,
    habitUid: String? = null
) {
    val (habit, onHabitChanged) = remember {
        mutableStateOf(Habit(uid = "temp"))
    }
    LaunchedEffect(Unit) {
        editHabitViewModel.getHabitById(habitUid)?.let {
            onHabitChanged(it)
        }
    }
    var nameFieldError by remember { mutableStateOf(false) }
    var descriptionFieldError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .fillMaxSize()
            .testTag("AddScreen"),
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
                isError = nameFieldError,
                modifier = Modifier.testTag("titleAddScreen")
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
                isError = descriptionFieldError,
                modifier = Modifier.testTag("descriptionAddScreen")
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
        val context = LocalContext.current
        Button(
            onClick = {
                nameFieldError = habit.name.isEmpty()
                descriptionFieldError = habit.description.isEmpty()
                if (!nameFieldError && !descriptionFieldError) {
                    val request = if (habit.uid != habitUid)
                        editHabitViewModel.addHabit(habit)
                    else
                        editHabitViewModel.editHabit(habit)
                    val errorText =
                        if (habit.uid != habitUid) R.string.edit_habit_error_add else R.string.edit_habit_error_edit
                    request.catch {
                        showError(context, context.getString(errorText))
                    }.onCompletion {
                        closeScreen(habit)
                    }.launchIn(editHabitViewModel.viewModelScope)
                }
            },
            modifier = Modifier.testTag("addScreenAddButton")
        ) {
            val buttonTitleRes = if (habit.uid != habitUid) {
                R.string.add_habit_title_button
            } else {
                R.string.edit_habit_title_button
            }
            Text(stringResource(buttonTitleRes))
        }
    }
}

private fun showError(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}