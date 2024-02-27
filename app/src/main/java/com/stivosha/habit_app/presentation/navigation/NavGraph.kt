package com.stivosha.habit_app.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stivosha.habit_app.presentation.composable.screen.AddEditHabitScreen
import com.stivosha.habit_app.presentation.composable.screen.HabitsScreen
import com.stivosha.habit_app.presentation.composable.util.rememberMutableStateListOf
import com.stivosha.habit_app.presentation.model.Habit

@Composable
fun NavGraph(navController: NavHostController) {
    val items = rememberMutableStateListOf<Habit>()
    NavHost(
        navController = navController,
        startDestination = NavRoute.HabitsList.path,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        habitScreen(items, navController, this)
        addHabitScreen(items, navController, this)
        editHabitScreen(items, navController, this)
    }
}

private fun habitScreen(
    items: SnapshotStateList<Habit>,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.HabitsList.path) {
        HabitsScreen(
            items = items.toList(),
            onHabitClicked = {
                navController.navigate(NavRoute.EditHabit.createRoute(it))
            },
            onFabClicked = {
                navController.navigate(NavRoute.AddHabit.path)
            }
        )
    }
}

private fun addHabitScreen(
    items: SnapshotStateList<Habit>,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.AddHabit.path) {
        AddEditHabitScreen(
            addHabit = {
                items.add(it)
                navController.popBackStack()
            }
        )
    }
}

private fun editHabitScreen(
    items: SnapshotStateList<Habit>,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.EditHabit.path) {
        val habit = NavRoute.EditHabit.fromArguments(it.arguments)
        AddEditHabitScreen(
            habitExtras = habit,
            addHabit = {
                items.add(it)
                navController.popBackStack()
            }
        )
    }
}