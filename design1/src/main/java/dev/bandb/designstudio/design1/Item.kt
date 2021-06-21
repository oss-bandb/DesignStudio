package dev.bandb.designstudio.design1

import dev.bandb.designstudio.design1.common.Task
import kotlinx.datetime.LocalDate

sealed class Item(val layoutId: Int) {
    data class TaskHeaderItem(val date: LocalDate) : Item(R.layout.task_header_item)

    data class TaskItem(val task: Task) : Item(R.layout.task_item)
}