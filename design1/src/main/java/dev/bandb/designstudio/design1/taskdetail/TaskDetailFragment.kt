package dev.bandb.designstudio.design1.taskdetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dev.bandb.designstudio.design1.BaseFragment
import dev.bandb.designstudio.design1.R
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.TaskDetailFragmentBinding
import dev.bandb.designstudio.design1.utils.recycler.SegmentedDividerItemDecoration

class TaskDetailFragment : BaseFragment() {
    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var binding: TaskDetailFragmentBinding
    private lateinit var taskGroup: TaskGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
        binding = TaskDetailFragmentBinding.inflate(inflater, container, false)
        taskGroup = SampleData.taskGroups[args.taskGroupId]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSharedEnterTransition()
        postponeEnterTransition()

        setIcon()
        setColors()
        setupToolbar()

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
            findNavController().navigate(
                TaskDetailFragmentDirections.createNewTask(
                    args.taskGroupId
                ), extras
            )
        }

    }

    private fun setupToolbar() {
        binding.tasksToolbar.leftAction.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_arrow))
        binding.tasksToolbar.rightAction.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_more_vert))
        binding.tasksToolbar.toolbarTitle.visibility = View.INVISIBLE

        binding.tasksToolbar.leftAction.setOnClickListener {
            findNavController().navigateUp()
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
    private fun createItemsWithHeaders(): MutableList<TaskDetailItem> {
        val dateHeaders = taskGroup.tasks.sortedBy { it.createdAt }.groupBy { it.dueAt }
        val items = mutableListOf<TaskDetailItem>()
        dateHeaders.entries.forEach { entry ->
            items.add(TaskDetailItem.TaskHeaderItem(entry.key))

            entry.value.forEach { task -> items.add(TaskDetailItem.TaskItem(task)) }
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
            .inflateTransition(R.transition.change_transform).apply {
                duration = 600
            }
    }
}

