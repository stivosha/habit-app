package com.stivosha.habit_app.presentation.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stivosha.habit_app.presentation.HabitViewModel
import com.stivosha.habit_app.presentation.composable.components.HabitItem
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.ui.theme.HabitappTheme
import kotlinx.coroutines.flow.collect

@Composable
fun HabitsScreen(
    viewModel: HabitViewModel,
    onHabitClicked: (Habit) -> Unit,
    onFabClicked: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClicked
            ) {
                Icon(Icons.Filled.Add, "Open create habit screen")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.padding(16.dp)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewModel.items.forEach {
                item {
                    HabitItem(
                        item = it,
                        onItemClick = { onHabitClicked(it) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HabitappTheme {
        HabitsScreen(viewModel(), {}, {})
    }
}