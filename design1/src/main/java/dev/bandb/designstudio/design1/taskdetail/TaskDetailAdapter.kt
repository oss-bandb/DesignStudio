package dev.bandb.designstudio.design1.taskdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bandb.designstudio.design1.R
import dev.bandb.designstudio.design1.common.Task
import dev.bandb.designstudio.design1.common.utils.isToday
import dev.bandb.designstudio.design1.common.utils.isTomorrow
import dev.bandb.designstudio.design1.databinding.TaskDetailHeaderItemBinding
import dev.bandb.designstudio.design1.databinding.TaskDetailItemBinding
import kotlinx.datetime.LocalDate

class TaskAdapter(private val items: List<TaskDetailItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.task_detail_item -> TaskViewHolder(TaskDetailItemBinding.inflate(inflater, parent, false))
            R.layout.task_detail_header_item -> TaskHeaderViewHolder(
                TaskDetailHeaderItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> holder.bind((items[position] as TaskDetailItem.TaskItem).task)
            is TaskHeaderViewHolder -> holder.bind((items[position] as TaskDetailItem.TaskHeaderItem))
            else -> throw IllegalStateException("Unknown viewHolder $holder")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].layoutId
}

private class TaskViewHolder(private val binding: TaskDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        binding.taskName.text = task.name
        binding.taskChecked.isChecked = task.finished
    }
}

class TaskHeaderViewHolder(private val binding: TaskDetailHeaderItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(headerItem: TaskDetailItem.TaskHeaderItem) {
        val date = headerItem.date
        binding.taskDateHeader.text = formatDate(date)
    }

    private fun formatDate(date: LocalDate): String =
        when {
            date.isToday() -> "Today"
            date.isTomorrow() -> "Tomorrow"
            else -> "$date"
        }
}

