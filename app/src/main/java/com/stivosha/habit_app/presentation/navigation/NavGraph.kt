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
import com.stivosha.habit_app.presentation.EditHabitViewModel
import com.stivosha.habit_app.presentation.HabitsViewModel
import com.stivosha.habit_app.presentation.composable.screen.AddEditHabitScreen
import com.stivosha.habit_app.presentation.composable.screen.HabitsScreen

@Composable
fun MyApp(navController: NavHostController) {
    val habitsViewModel: HabitsViewModel = viewModel()
    val editHabitViewModel: EditHabitViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoute.HabitsList.path,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        habitScreen(habitsViewModel, navController)
        addHabitScreen(editHabitViewModel, navController)
        editHabitScreen(editHabitViewModel, navController)
    }
}

private fun NavGraphBuilder.habitScreen(
    habitsViewModel: HabitsViewModel,
    navController: NavHostController
) {
    composable(route = NavRoute.HabitsList.path) {
        HabitsScreen(
            viewModel = habitsViewModel,
            onHabitClicked = {
                navController.navigate(NavRoute.EditHabit.createRoute(it))
            },
            onFabClicked = {
                navController.navigate(NavRoute.AddHabit.path)
            }
        )
    }
}

private fun NavGraphBuilder.addHabitScreen(
    editHabitViewModel: EditHabitViewModel,
    navController: NavHostController
) {
    composable(route = NavRoute.AddHabit.path) {
        AddEditHabitScreen(
            editHabitViewModel,
            closeScreen = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.editHabitScreen(
    editHabitViewModel: EditHabitViewModel,
    navController: NavHostController
) {
    composable(
        route = NavRoute.EditHabit.path,
        arguments = listOf(navArgument("habitId") { type = NavType.LongType })
    ) { backStackEntry ->
        val habitId = backStackEntry.arguments?.getLong("habitId")
        AddEditHabitScreen(
            editHabitViewModel,
            closeScreen = { navController.popBackStack() },
            habitId
        )
    }
}