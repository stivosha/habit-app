package com.stivosha.habit_app.presentation.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stivosha.habit_app.R
import com.stivosha.habit_app.presentation.HabitsViewModel
import com.stivosha.habit_app.presentation.composable.components.HabitItem
import com.stivosha.habit_app.presentation.model.Habit
import com.stivosha.habit_app.presentation.model.Type
import com.stivosha.habit_app.ui.theme.HabitappTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel,
    onHabitClicked: (Habit) -> Unit,
    onFabClicked: () -> Unit
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Хорошие", "Плохие")
    val drawerItems = listOf("Привычки", "Инфо")
    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedDrawerIndex by rememberSaveable { mutableIntStateOf(0) }
    val habits by viewModel.habits.collectAsState()

    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> viewModel.fetchData()
                else -> {}
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val badHabits = remember(habits) {
        habits.filter { it.type == Type.BAD }
    }
    val goodHabits = remember(habits) {
        habits.filter { it.type == Type.GOOD }
    }

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

                    val applyFilter = { name: String ->
                        viewModel.addFilter(name)
                    }

                    when (tabIndex) {
                        0 -> HabitsContent(
                            scope,
                            goodHabits,
                            onHabitClicked,
                            onFabClicked,
                            applyFilter
                        )

                        1 -> HabitsContent(
                            scope,
                            badHabits,
                            onHabitClicked,
                            onFabClicked,
                            applyFilter
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
    scope: CoroutineScope,
    habits: List<Habit>,
    onHabitClicked: (Habit) -> Unit,
    onFabClicked: () -> Unit,
    applyFilter: (String) -> Unit
) {
    val (showBottomSheet, changeBottomSheetVisible) = remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { changeBottomSheetVisible(true) }) {
                        Icon(Icons.Filled.Search, contentDescription = "Localized description")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onFabClicked
                    ) {
                        Icon(Icons.Filled.Add, "Open create habit screen")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = habits) {
                HabitItem(
                    item = it,
                    onItemClick = onHabitClicked
                )
            }
        }
        FilterBottomSheet(
            scope,
            showBottomSheet,
            changeBottomSheetVisible,
            applyFilter
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    scope: CoroutineScope,
    showBottomSheet: Boolean,
    changeBottomSheetVisible: (Boolean) -> Unit,
    applyFilter: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { changeBottomSheetVisible(false) },
            sheetState = sheetState
        ) {
            var text by remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth()
                )
                FilledTonalButton(
                    onClick = {
                        applyFilter(text)
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                changeBottomSheetVisible(false)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.habit_filter_filter_by_name))
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