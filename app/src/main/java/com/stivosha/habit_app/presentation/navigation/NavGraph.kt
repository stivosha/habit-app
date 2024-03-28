package com.stivosha.habit_app.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stivosha.habit_app.presentation.HabitViewModel
import com.stivosha.habit_app.presentation.composable.screen.AddEditHabitScreen
import com.stivosha.habit_app.presentation.composable.screen.HabitsScreen

@Composable
fun MyApp(navController: NavHostController) {
    val habitViewModel: HabitViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoute.HabitsList.path,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        habitScreen(habitViewModel, navController, this)
        addHabitScreen(habitViewModel, navController, this)
        editHabitScreen(habitViewModel, navController, this)
    }
}

private fun habitScreen(
    habitViewModel: HabitViewModel,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.HabitsList.path) {
        HabitsScreen(
            viewModel = habitViewModel,
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
    habitViewModel: HabitViewModel,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.AddHabit.path) {
        AddEditHabitScreen(
            addHabit = {
                habitViewModel.addHabit(it)
                navController.popBackStack()
            }
        )
    }
}

private fun editHabitScreen(
    habitViewModel: HabitViewModel,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.EditHabit.path,
        arguments = listOf(navArgument("habitId") { type = NavType.LongType })
    ) { backStackEntry ->
        val habitId = backStackEntry.arguments?.getLong("habitId")
        AddEditHabitScreen(
            habitExtras = habitViewModel.getById(habitId),
            addHabit = {
                habitViewModel.editHabit(it)
                navController.popBackStack()
            }
        )
    }
}