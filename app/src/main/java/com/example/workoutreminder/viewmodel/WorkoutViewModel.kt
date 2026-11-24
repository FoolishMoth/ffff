package com.example.workoutreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutreminder.data.WorkoutDatabase
import com.example.workoutreminder.data.WorkoutEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val db = WorkoutDatabase.getInstance(application)
    private val dao = db.workoutDao()

    val entries = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveInstruction(dayOfWeek: Int, instruction: String) {
        viewModelScope.launch {
            dao.insert(WorkoutEntry(dayOfWeek, instruction))
        }
    }
}
