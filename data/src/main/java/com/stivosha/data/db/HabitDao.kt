package com.stivosha.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitDbEntity>>

    @Query("SELECT * FROM habits WHERE uid=:uid")
    suspend fun getById(uid: String): HabitDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: HabitDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg habits: HabitDbEntity)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Update
    suspend fun update(habit: HabitDbEntity)
}