package com.stivosha.habit_app.data.remote

import com.stivosha.habit_app.TOKEN
import com.stivosha.habit_app.presentation.model.Habit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface HabitApi {
    @Headers("Authorization: $TOKEN")
    @GET("habit")
    suspend fun getHabits(): List<HabitEntity>

    @Headers("Authorization: $TOKEN")
    @PUT("habit")
    suspend fun putHabit(@Body habit: HabitEntity)
}