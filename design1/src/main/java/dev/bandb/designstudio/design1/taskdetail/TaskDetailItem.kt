package dev.bandb.designstudio.design1.taskdetail

import dev.bandb.designstudio.design1.R
import dev.bandb.designstudio.design1.common.Task
import kotlinx.datetime.LocalDate

sealed class TaskDetailItem(val layoutId: Int) {
    data class TaskHeaderItem(val date: LocalDate) : TaskDetailItem(R.layout.task_detail_header_item)

    data class TaskItem(val task: Task) : TaskDetailItem(R.layout.task_detail_item)
}