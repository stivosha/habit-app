package com.stivosha.habit_app.presentation.composable.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun HabitNavigationDrawer() {
    val items = listOf("Привычки", "Инфо")
    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    Surface {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    items.forEachIndexed { index, drawerItem ->
                        NavigationDrawerItem(label = {
                            Text(text = drawerItem)
                        }, selected = index == selectedItemIndex, onClick = {
                            selectedItemIndex = index
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
            val openDrawer = { scope.launch { navigationState.open() } }
        }
    }
}