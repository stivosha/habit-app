package com.stivosha.habit_app.presentation.composable.components

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stivosha.domain.Habit
import com.stivosha.habit_app.R

@Composable
fun PeriodicityTextField(
    habit: Habit,
    habitChanged: (Habit) -> Unit
) {
    Row {
        Text(stringResource(R.string.periodicity_text_part1))
        NumberTextField(
            value = if (habit.times == null) "" else habit.times.toString(),
            onValueChange = {
                if (it.length <= 2) {
                    if (it.isNotBlank()) {
                        habitChanged(habit.copy(times = it.toInt()))
                    }
                }
            }
        )
        Text(stringResource(R.string.periodicity_text_part2))
        NumberTextField(
            value = if (habit.period == null) "" else habit.period.toString(),
            onValueChange = {
                if (it.length <= 2) {
                    if (it.isNotBlank()) {
                        habitChanged(habit.copy(period = it.toInt()))
                    }
                }
            }
        )
        Text(stringResource(R.string.periodicity_text_part3))
    }
}

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    val isDarkTheme = isDarkTheme(LocalContext.current)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(
            color = if (isDarkTheme) Color.White else Color.Black,
            fontSize = 14.sp
        ),
        cursorBrush = SolidColor(if (isDarkTheme) Color.White else Color.Black),
        modifier = Modifier
            .width(24.dp)
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

private fun isDarkTheme(context: Context): Boolean {
    val currentNightMode =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}