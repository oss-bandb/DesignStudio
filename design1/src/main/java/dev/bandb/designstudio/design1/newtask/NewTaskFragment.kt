package dev.bandb.designstudio.design1.newtask

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.*
import com.google.android.samples.insetsanimation.RootViewDeferringInsetsCallback
import dev.bandb.designstudio.design1.BaseFragment
import dev.bandb.designstudio.design1.R
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.NewTaskFragmentBinding
import dev.bandb.designstudio.design1.databinding.NewTaskGroupItemBinding
import dev.bandb.designstudio.design1.utils.showSoftKeyboard
import dev.bandb.designstudio.design1.utils.windowinsetanimation.ControlFocusInsetsAnimationCallback
import dev.bandb.designstudio.design1.utils.windowinsetanimation.TranslateDeferringInsetsAnimationCallback


class NewTaskFragment : BaseFragment() {

    private val args: NewTaskFragmentArgs by navArgs()
    private lateinit var binding: NewTaskFragmentBinding
    private lateinit var taskGroup: TaskGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewTaskFragmentBinding.inflate(layoutInflater, container, false)
        taskGroup = SampleData.taskGroups[args.taskGroupId]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            duration = 600
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            duration = 600
        }

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = FastOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
            duration = 600
            setPathMotion(MaterialArcMotion())
        }

        setColors()
        setUpToolbar()

        binding.newTaskField.showSoftKeyboard()

        postponeEnterTransition()
        // XXX: possibly misunderstood
        binding.newTaskGroupList.apply {
            adapter = TaskGroupNameAdapter(SampleData.taskGroups)

            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        setUpWindowInsetAnimation()
    }

    private fun setUpWindowInsetAnimation() {
        val deferringInsetsListener = RootViewDeferringInsetsCallback(
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
        ViewCompat.setWindowInsetsAnimationCallback(binding.root, deferringInsetsListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root, deferringInsetsListener)

        ViewCompat.setWindowInsetsAnimationCallback(
            binding.newTaskCreate,
            TranslateDeferringInsetsAnimationCallback(
                view = binding.newTaskCreate,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime(),
                // We explicitly allow dispatch to continue down to binding.messageHolder's
                // child views, so that step 2.5 below receives the call
                dispatchMode = WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
            )
        )

        ViewCompat.setWindowInsetsAnimationCallback(
            binding.newTaskField,
            ControlFocusInsetsAnimationCallback(binding.newTaskField)
        )
    }

    private fun setUpToolbar() {
        binding.newTaskToolbar.leftAction.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_close))
        binding.newTaskToolbar.toolbarTitle.text = getString(R.string.new_task_toolbar_title)
        binding.newTaskToolbar.rightAction.visibility = View.INVISIBLE

        binding.newTaskToolbar.leftAction.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setColors() {
        taskGroup.color?.let { color ->
            binding.newTaskCreate.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(color))
        }
    }
}

class TaskGroupNameAdapter(private val taskGroups: List<TaskGroup>) :
    RecyclerView.Adapter<TaskGroupNameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskGroupNameViewHolder(
            NewTaskGroupItemBinding.inflate(
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

class TaskGroupNameViewHolder(private val binding: NewTaskGroupItemBinding) :
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