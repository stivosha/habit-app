package com.stivosha.habit_app

import android.app.Application
import androidx.room.Room
import com.stivosha.habit_app.data.HabitRepositoryImpl
import com.stivosha.habit_app.data.db.HabitDatabase
import com.stivosha.habit_app.data.remote.HabitApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
const val TOKEN = "5c6bcb8a-f99b-4564-8112-5d38e5c2f543"

class App : Application() {

    companion object {
        lateinit var repository: HabitRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java, "habit-database"
        ).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(HabitApi::class.java)
        repository = HabitRepositoryImpl(db.habitDao(), api)
    }
}