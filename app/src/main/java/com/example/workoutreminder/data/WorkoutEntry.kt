package com.example.workoutreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_entries")
data class WorkoutEntry(
    @PrimaryKey val dayOfWeek: Int, // 1 = Понедельник, 7 = Воскресенье
    val instruction: String
)
