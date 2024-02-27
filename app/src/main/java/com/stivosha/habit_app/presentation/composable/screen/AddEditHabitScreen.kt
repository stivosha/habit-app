package com.stivosha.habit_app.presentation.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.composable.AddHabitElevatedCard
import com.stivosha.habit_app.presentation.composable.ColorPickerGroup
import com.stivosha.habit_app.presentation.composable.PeriodicityTextField
import com.stivosha.habit_app.presentation.composable.PriorityExposedDropdown
import com.stivosha.habit_app.presentation.composable.TypeRadioButtonGroup
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.ui.theme.HabitappTheme

@Composable
fun AddEditHabitScreen(
    addHabit: (Habit) -> Unit,
    habitExtras: Habit? = null
) {
    val habit = rememberSaveable { mutableStateOf(habitExtras ?: Habit()) }
    var nameFieldError by remember { mutableStateOf(false) }
    var descriptionFieldError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_name_description
        ) {
            OutlinedTextField(
                value = habit.value.name,
                onValueChange = { text ->
                    nameFieldError = text.isEmpty()
                    habit.value = habit.value.copy(name = text)
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
                value = habit.value.description,
                onValueChange = { text ->
                    descriptionFieldError = text.isEmpty()
                    habit.value = habit.value.copy(description = text)
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
            PriorityExposedDropdown(habit)
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_type
        ) {
            TypeRadioButtonGroup(habit)
        }
        AddHabitElevatedCard(
            titleRes = R.string.add_habit_title_color
        ) {
            ColorPickerGroup(habit)
        }
        Button(onClick = {
            nameFieldError = habit.value.name.isEmpty()
            descriptionFieldError = habit.value.description.isEmpty()
            if (!nameFieldError && !descriptionFieldError) {
                addHabit(habit.value)
            }
        }) {
            Text(stringResource(R.string.add_habit_title_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddHabitScreenPreview() {
    HabitappTheme {
        //AddEditHabitScreen({})
        var inputText by remember { mutableStateOf("") }
        val primaryColor = MaterialTheme.colorScheme.primary
        Row {
            Text("Введите кол-во вещей ")
            BasicTextField(
                value = inputText,
                onValueChange = { if (it.length <= 2) inputText = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(18.dp)
                    .drawBehind {
                        val borderSize = 2.dp.toPx()
                        drawLine(
                            color = primaryColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    }
            )
            Text(" в машине")
        }
    }
}