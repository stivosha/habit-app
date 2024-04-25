package com.stivosha.habit_app.presentation.composable.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stivosha.domain.Habit
import com.stivosha.domain.Priority.HIGH
import com.stivosha.domain.Priority.LOW
import com.stivosha.domain.Priority.MEDIUM
import com.stivosha.habit_app.R

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
                    HIGH -> R.string.add_habit_high_priority
                    MEDIUM -> R.string.add_habit_medium_priority
                    LOW -> R.string.add_habit_low_priority
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
                    habitChanged(habit.copy(priority = HIGH))
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.add_habit_medium_priority)) },
                onClick = {
                    habitChanged(habit.copy(priority = MEDIUM))
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.add_habit_low_priority)) },
                onClick = {
                    habitChanged(habit.copy(priority = LOW))
                    isExpanded = false
                }
            )
        }
    }
}