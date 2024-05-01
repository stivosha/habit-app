package com.stivosha.habit_app

import com.stivosha.domain.Habit
import com.stivosha.domain.HabitRepository
import com.stivosha.habit_app.presentation.HabitsState
import com.stivosha.habit_app.presentation.HabitsState.Loading
import com.stivosha.habit_app.presentation.HabitsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HabitsUnitTest {

    private val simpleData = flowOf(listOf(Habit("123")))
    private val repository: HabitRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testHabitsViewModel() = runTest {
        `when`(repository.observeHabits()).thenReturn(simpleData)
        val viewModel = HabitsViewModel(repository)
        assertEquals(viewModel.state.value, Loading)
        viewModel.fetchData()
        advanceTimeBy(50L)
        assertEquals(viewModel.state.value is HabitsState.Data, true)
    }

    @Test
    fun testErrorHabitsViewModel() = runTest {
        `when`(repository.observeHabits()).thenReturn(simpleData)
        `when`(repository.loadHabits()).thenReturn(flow { throw NullPointerException() })
        val viewModel = HabitsViewModel(repository)
        assertEquals(viewModel.state.value, Loading)
        viewModel.fetchData()
        advanceTimeBy(50L)
        assertEquals(viewModel.state.value is HabitsState.Data, true)
        assertEquals(
            (viewModel.state.value as HabitsState.Data).errorText,
            "Ошибка подгрузки данных"
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }
}