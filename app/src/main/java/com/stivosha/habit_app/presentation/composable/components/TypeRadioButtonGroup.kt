package com.stivosha.habit_app.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stivosha.domain.Habit
import com.stivosha.domain.Type.BAD
import com.stivosha.domain.Type.GOOD
import com.stivosha.habit_app.R

@Composable
fun TypeRadioButtonGroup(
    habit: Habit,
    habitChanged: (Habit) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = habit.type == GOOD,
                onClick = { habitChanged(habit.copy(type = GOOD)) }
            )
            Text(stringResource(R.string.add_habit_good_type))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = habit.type == BAD,
                onClick = { habitChanged(habit.copy(type = BAD)) }
            )
            Text(stringResource(R.string.add_habit_bad_type))
        }
    }
}