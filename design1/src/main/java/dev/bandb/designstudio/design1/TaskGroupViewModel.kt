package dev.bandb.designstudio.design1

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class TaskGroupViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // TODO create action to set new background color with animate parameter
    val backgroundColor: LiveData<Int> =
        savedStateHandle.getLiveData("color", R.color.default_background_color)

    fun setBackgroundColor(color: Int) {
        savedStateHandle["color"] = color
    }
}