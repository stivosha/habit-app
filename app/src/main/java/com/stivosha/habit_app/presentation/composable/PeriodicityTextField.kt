package com.stivosha.habit_app.presentation.composable

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stivosha.habit_app.presentation.composable.screen.AddEditHabitScreen
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.ui.theme.HabitappTheme

@Composable
fun PeriodicityTextField(
    habit: MutableState<Habit>
) {
    Row {
        Text("Повторять ")
        NumberTextField(
            value = { habit.value.times },
            onValueChange = {
                if (it.length <= 2) {
                    habit.value = habit.value.copy(times = it.toInt())
                }
            }
        )
        Text(" раз в ")
        NumberTextField(
            value = {
                habit.value.period
            },
            onValueChange = {
                if (it.length <= 2) {
                    habit.value = habit.value.copy(times = it.toInt())
                }
            }
        )
        Text(" дней")
    }
}

@Composable
fun NumberTextField(
    value: () -> Int,
    onValueChange: (String) -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    BasicTextField(
        value = value.toString(),
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .width(18.dp)
            .drawBehind {
                val borderSize = 2.dp.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
    )
}

@Preview(showBackground = true)
@Composable
fun PeriodicityTextFieldPreview() {
    val state = remember { mutableStateOf(Habit()) }
    HabitappTheme {
        PeriodicityTextField(state)
    }
}