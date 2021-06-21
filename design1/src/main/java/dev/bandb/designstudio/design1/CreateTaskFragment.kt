package dev.bandb.designstudio.design1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.CreateTaskFragmentBinding
import dev.bandb.designstudio.design1.databinding.CreateTaskGroupItemBinding

class CreateTaskFragment : BaseFragment() {

    private lateinit var binding: CreateTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateTaskFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.change_transform)
        postponeEnterTransition()

        binding.newTaskGroupList.apply {
            adapter = TaskGroupNameAdapter(SampleData.taskGroups)

            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

    }
}

class TaskGroupNameAdapter(private val taskGroups: List<TaskGroup>) :
    RecyclerView.Adapter<TaskGroupNameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskGroupNameViewHolder(
            CreateTaskGroupItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskGroupNameViewHolder, position: Int) {
        holder.bind(taskGroups[position])
    }

    override fun getItemCount(): Int = taskGroups.size

}

class TaskGroupNameViewHolder(private val binding: CreateTaskGroupItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(taskGroup: TaskGroup) {
        binding.groupName.text = taskGroup.name

        taskGroup.icon?.let { iconRes ->
            val icon = ContextCompat.getDrawable(context, iconRes)
            binding.groupIcon.setImageDrawable(icon)
        }
    }
}