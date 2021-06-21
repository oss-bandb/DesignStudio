package dev.bandb.designstudio.design1

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.TaskGroupItemBinding
import dev.bandb.designstudio.design1.transition.TaskTransitionData

internal class TaskGroupsAdapter(
    private val taskGroups: List<TaskGroup>,
    private val onTaskGroupSelected: (TaskTransitionData) -> Unit
) :
    RecyclerView.Adapter<TaskGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskGroupViewHolder(
            TaskGroupItemBinding.inflate(inflater, parent, false),
            onTaskGroupSelected
        )
    }

    override fun onBindViewHolder(holder: TaskGroupViewHolder, position: Int) {
        holder.bind(taskGroups[position])
    }

    override fun getItemCount(): Int = taskGroups.size
}

class TaskGroupViewHolder(
    private val binding: TaskGroupItemBinding,
    private val onTaskGroupSelected: (TaskTransitionData) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    init {
        itemView.setOnClickListener {
            onTaskGroupSelected(
                TaskTransitionData(
                    adapterPosition,
                    arrayOf(
                        binding.taskIconContainer to context.getString(R.string.task_icon_transition_name),
                        binding.tasksGroupName to context.getString(R.string.tasks_group_name_transition_name),
                        binding.tasksProgressbar to context.getString(R.string.tasks_progress_bar_transition_name),
                        binding.tasksCount to context.getString(R.string.tasks_count_transition_name),
                        binding.tasksProgress to context.getString(R.string.task_progress_transition_name),
                        binding.root to context.getString(R.string.task_create_layout_transition_name)
                    )
                )
            )
        }
    }

    fun bind(taskGroup: TaskGroup) {
        val taskSize = taskGroup.tasks.size
        val finishedTasks = taskGroup.tasks.count { it.finished }

        setTransitionNames()

        binding.tasksGroupName.text = taskGroup.name
        binding.tasksCount.text = context.getString(R.string.task_count, taskSize)
        binding.tasksProgressbar.max = taskSize
        binding.tasksProgressbar.progress = finishedTasks
        taskGroup.color?.let { color ->
            binding.tasksProgressbar.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, color))
        }

        taskGroup.icon?.let { iconRes ->
            val icon = ContextCompat.getDrawable(context, iconRes)?.apply {
                taskGroup.color?.let { color ->
                    setTint(context.getColor(color))
                }
            }
            binding.taskIcon.setImageDrawable(icon)
        }

        if (taskSize > 0) {
            binding.tasksProgress.text =
                context.getString(R.string.task_percentage, (100 / taskSize * finishedTasks))
        }
    }

    private fun setTransitionNames() {
//        val transitionName =
        binding.root.transitionName = "test_$adapterPosition"
        binding.taskIconContainer.transitionName =
            context.getString(R.string.task_icon_transition_name) + "_$adapterPosition"
        binding.tasksGroupName.transitionName =
            context.getString(R.string.tasks_group_name_transition_name) + "_$adapterPosition"
        binding.tasksProgressbar.transitionName =
            context.getString(R.string.tasks_progress_bar_transition_name) + "_$adapterPosition"
        // TODO: 20-05-2021 20:53 The transitions do not work on changing the font size
        //  Custom solution presented at Google I/O 2016: https://www.youtube.com/watch?v=4L4fLrWDvAU&t=1051s
        binding.tasksCount.transitionName =
            context.getString(R.string.tasks_count_transition_name) + "_$adapterPosition"
        binding.tasksProgress.transitionName =
            context.getString(R.string.task_progress_transition_name) + "_$adapterPosition"
    }
}

