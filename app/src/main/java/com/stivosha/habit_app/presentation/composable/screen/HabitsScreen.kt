package com.stivosha.habit_app.presentation.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.HabitViewModel
import com.stivosha.habit_app.presentation.composable.components.HabitItem
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Type
import com.stivosha.habit_app.ui.theme.HabitappTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    viewModel: HabitViewModel,
    onHabitClicked: (Habit) -> Unit,
    onFabClicked: () -> Unit
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Хорошие", "Плохие")
    val drawerItems = listOf("Привычки", "Инфо")
    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedDrawerIndex by rememberSaveable { mutableIntStateOf(0) }
    Surface {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    drawerItems.forEachIndexed { index, drawerItem ->
                        NavigationDrawerItem(label = {
                            Text(text = drawerItem)
                        }, selected = index == selectedDrawerIndex, onClick = {
                            selectedDrawerIndex = index
                            scope.launch {
                                navigationState.close()
                            }
                        },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = navigationState,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val appBarTitle = if (selectedDrawerIndex == 0) {
                    stringResource(R.string.habit_screen_title)
                } else {
                    stringResource(R.string.info_screen_title)
                }
                TopAppBar(
                    title = { Text(appBarTitle) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { navigationState.open() } }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
                if (selectedDrawerIndex == 0) {
                    TabRow(selectedTabIndex = tabIndex) {
                        tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(title) },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index }
                            )
                        }
                    }
                    when (tabIndex) {
                        0 -> HabitsContent(
                            viewModel.items.filter { it.type == Type.GOOD },
                            onHabitClicked,
                            onFabClicked
                        )

                        1 -> HabitsContent(
                            viewModel.items.filter { it.type == Type.BAD },
                            onHabitClicked,
                            onFabClicked
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Info")
                    }
                }
            }
        }
    }
}

@Composable
fun HabitsContent(
    habits: List<Habit>,
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
            habits.forEach {
                item {
                    HabitItem(
                        item = it,
                        onItemClick = onHabitClicked
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