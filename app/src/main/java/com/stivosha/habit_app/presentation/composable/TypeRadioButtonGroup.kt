package com.stivosha.habit_app.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Type
import com.stivosha.habit_app.presentation.model.Type.BAD
import com.stivosha.habit_app.presentation.model.Type.GOOD

@Composable
fun TypeRadioButtonGroup(
    habit: MutableState<Habit>,
) {
    Column(Modifier.selectableGroup()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = habit.value.type == GOOD,
                onClick = { habit.value = habit.value.copy(type = GOOD) }
            )
            Text(stringResource(R.string.add_habit_good_type))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = habit.value.type == BAD,
                onClick = { habit.value = habit.value.copy(type = BAD) }
            )
            Text(stringResource(R.string.add_habit_bad_type))
        }
    }
}

@Preview
@Composable
fun Preview() {
    val state = remember { mutableStateOf(Habit()) }
    TypeRadioButtonGroup(state)
}