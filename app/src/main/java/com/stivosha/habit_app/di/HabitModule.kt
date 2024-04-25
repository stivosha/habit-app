package com.stivosha.habit_app.di

import android.content.Context
import androidx.room.Room
import com.stivosha.data.HabitRepositoryImpl
import com.stivosha.data.db.HabitDao
import com.stivosha.data.db.HabitDatabase
import com.stivosha.data.remote.HabitApi
import com.stivosha.domain.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

@Module
@InstallIn(SingletonComponent::class)
object HabitModule {

    @Provides
    fun provideRepository(
        dao: HabitDao,
        api: HabitApi
    ): HabitRepository = HabitRepositoryImpl(dao, api)

    @Provides
    fun provideHabitDao(@ApplicationContext context: Context): HabitDao = Room.databaseBuilder(
        context,
        HabitDatabase::class.java, "habit-database"
    ).build().habitDao()

    @Provides
    fun provideHabitApi(): HabitApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HabitApi::class.java)
}