package com.stivosha.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

const val TOKEN = "5c6bcb8a-f99b-4564-8112-5d38e5c2f543"

interface HabitApi {
    @Headers("Authorization: $TOKEN")
    @GET("habit")
    suspend fun getHabits(): List<HabitRemoteEntity>

    @Headers("Authorization: $TOKEN")
    @PUT("habit")
    suspend fun putHabit(@Body habit: HabitRemoteEntity)
}