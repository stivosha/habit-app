package com.stivosha.habit_app.presentation.composable.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityExposedDropdown(
    habit: Habit,
    habitChanged: (Habit) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        TextField(
            value = stringResource(
                when (habit.priority) {
                    Priority.HIGH -> R.string.add_habit_high_priority
                    Priority.MEDIUM -> R.string.add_habit_medium_priority
                    Priority.LOW -> R.string.add_habit_low_priority
                }
            ),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.add_habit_high_priority)) },
                onClick = {
                    habitChanged(habit.copy(priority = Priority.HIGH))
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.add_habit_medium_priority)) },
                onClick = {
                    habitChanged(habit.copy(priority = Priority.MEDIUM))
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.add_habit_low_priority)) },
                onClick = {
                    habitChanged(habit.copy(priority = Priority.LOW))
                    isExpanded = false
                }
            )
        }
    }
}