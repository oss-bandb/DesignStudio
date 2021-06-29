package dev.bandb.designstudio.design1

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.TasksFragmentBinding
import dev.bandb.designstudio.design1.recycler.SegmentedDividerItemDecoration
import dev.bandb.designstudio.design1.transition.Keep

class TasksFragment : BaseFragment() {
    private val args: TasksFragmentArgs by navArgs()
    private lateinit var binding: TasksFragmentBinding
    private lateinit var taskGroup: TaskGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TasksFragmentBinding.inflate(inflater, container, false)
        taskGroup = SampleData.taskGroups[args.taskGroupId]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSharedEnterTransition()
        postponeEnterTransition()

        setIcon()
        setColors()

        val taskSize = taskGroup.tasks.size
        val finishedTasks = taskGroup.tasks.count { it.finished }

        binding.tasksGroupName.text = taskGroup.name
        binding.taskDetailCount.text = resources.getString(R.string.task_count, taskSize)
        binding.tasksProgressbar.max = taskSize
        binding.tasksProgressbar.progress = finishedTasks

        if (taskSize > 0) {
            binding.tasksProgress.text =
                resources.getString(
                    R.string.task_percentage,
                    (100 / taskSize * finishedTasks)
                )
        }

        binding.taskList.apply {
            adapter = TaskAdapter(createItemsWithHeaders())
            addItemDecoration(
                SegmentedDividerItemDecoration(requireContext())
            )

            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        binding.createTaskFab.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.createTaskFab to resources.getString(R.string.task_create_transition_name),
            )
            findNavController().navigate(TasksFragmentDirections.createNewTask(args.taskGroupId), extras)
        }

    }

    private fun setIcon() {
        taskGroup.icon?.let { iconRes ->
            val icon = ContextCompat.getDrawable(requireContext(), iconRes)?.apply {
                taskGroup.color?.let { color ->
                    setTint(requireContext().getColor(color))
                }
            }
            binding.taskIcon.setImageDrawable(icon)
        }
    }

    // TODO can this be made better?
    private fun createItemsWithHeaders(): MutableList<Item> {
        val dateHeaders = taskGroup.tasks.sortedBy { it.createdAt }.groupBy { it.dueAt }
        val items = mutableListOf<Item>()
        dateHeaders.entries.forEach { entry ->
            items.add(Item.TaskHeaderItem(entry.key))

            entry.value.forEach { task -> items.add(Item.TaskItem(task)) }
        }
        return items
    }

    private fun setColors() {
        taskGroup.color?.let { color ->
            binding.createTaskFab.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(color))

            binding.tasksProgressbar.progressTintList =
                ColorStateList.valueOf(requireContext().getColor(color))
        }
    }

    private fun setSharedEnterTransition() {
        // TODO: 20-05-2021 21:44 Change color of statusbar after transition
        // TODO: 21-05-2021 13:10 On slow animation speed we can see how the round corner from the
        //  card view changes to the hard corner of the animated view. Check if we need to change this again
        //  to use a card view in details again.
        // TODO: 26-05-2021 13:39 ^CardView does not change the transition
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.change_transform)
    }
}

