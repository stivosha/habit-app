package com.stivosha.habit_app.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stivosha.habit_app.presentation.model.Habit

@Composable
fun ColorPickerGroup(
    habit: MutableState<Habit>
) {
    val colors = listOf(
        0xFFFF0000,
        0xFF00FF00,
        0xFF0000FF
    )
    val active = remember { mutableLongStateOf(colors.first()) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        colors.forEach { hex ->
            val borderColor = if (hex == active.longValue) Color.White else Color.Transparent
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(hex))
                    .border(BorderStroke(1.dp, borderColor))
                    .clickable
                    {
                        habit.value = habit.value.copy(color = hex)
                        active.longValue = hex
                    },
            )
        }
    }
}

@Preview
@Composable
fun PreviewColorPickerGroup() {
    val state = remember { mutableStateOf(Habit()) }
    ColorPickerGroup(state)
}