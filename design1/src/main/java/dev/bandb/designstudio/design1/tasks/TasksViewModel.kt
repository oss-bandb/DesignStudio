package dev.bandb.designstudio.design1.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.bandb.designstudio.design1.R

class TasksViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // TODO create action to set new background color with animate parameter
    val backgroundColor: LiveData<Int> =
        savedStateHandle.getLiveData("color", R.color.salmon)

    fun setBackgroundColor(color: Int) {
        savedStateHandle["color"] = color
    }
}