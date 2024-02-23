package com.stivosha.habit_app.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stivosha.habit_app.ui.theme.HabitappTheme

@Composable
fun HabitListScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HabitappTheme {
        HabitListScreen()
    }
}