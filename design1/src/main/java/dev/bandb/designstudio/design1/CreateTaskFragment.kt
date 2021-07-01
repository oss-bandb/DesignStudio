package dev.bandb.designstudio.design1

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.*
import com.google.android.samples.insetsanimation.RootViewDeferringInsetsCallback
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import dev.bandb.designstudio.design1.databinding.CreateTaskFragmentBinding
import dev.bandb.designstudio.design1.databinding.CreateTaskGroupItemBinding
import dev.bandb.designstudio.design1.windowinsetanimation.ControlFocusInsetsAnimationCallback
import dev.bandb.designstudio.design1.windowinsetanimation.TranslateDeferringInsetsAnimationCallback


class CreateTaskFragment : BaseFragment() {

    private val args: CreateTaskFragmentArgs by navArgs()
    private lateinit var binding: CreateTaskFragmentBinding
    private lateinit var taskGroup: TaskGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateTaskFragmentBinding.inflate(layoutInflater, container, false)
        taskGroup = SampleData.taskGroups[args.taskGroupId]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = FastOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
            setPathMotion(MaterialArcMotion())
        }

        setColors()
        setUpToolbar()

        binding.newTaskField.showSoftKeyboard()

        postponeEnterTransition()
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
        binding.toolbar.toolbarTitle.text = "New Task"
        binding.toolbar.toolbarTitle.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
    }

    private fun setColors() {
        taskGroup.color?.let { color ->
            binding.newTaskCreate.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(color))
        }
    }
}

fun View.showSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
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