package com.example.workoutreminder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_entries ORDER BY dayOfWeek")
    fun getAll(): Flow<List<WorkoutEntry>>

    @Query("SELECT * FROM workout_entries WHERE dayOfWeek = :day")
    suspend fun getByDay(day: Int): WorkoutEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: WorkoutEntry)
}
