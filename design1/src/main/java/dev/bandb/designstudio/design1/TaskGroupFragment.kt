package dev.bandb.designstudio.design1

import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.databinding.TaskGroupFragmentBinding
import dev.bandb.designstudio.design1.recycler.PeekingItemDecoration
import dev.bandb.designstudio.design1.recycler.SnapScrollListener
import dev.bandb.designstudio.design1.transition.Keep
import dev.bandb.designstudio.design1.transition.TaskTransitionData


// TODO: 21-05-2021 22:56 Make background a gradient
// FIXME: 4-06-2021 13:12 Shadow of the card view items gets a solid 1 pixel line after the transition
// XXX: 4-06-2021 13:24 While transitioning this fragment should slide down, but it seems not to work correctly or not possible?
class TaskGroupFragment : BaseFragment() {

    private lateinit var binding: TaskGroupFragmentBinding
    private val viewModel: TaskGroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskGroupFragmentBinding.inflate(inflater, container, false)
        // FIXME first value somehow now always the default value
        setBackgroundColor(viewModel.backgroundColor.value ?: R.color.default_background_color)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        // TODO: 2-06-2021 14:34 Slide only content and increase the duration
        exitTransition = Keep()
        postponeEnterTransition()

        binding.profileImage.clipToOutline = true

        // TODO: 26-05-2021 14:20 This seems strange in combination with line 36
        SampleData.taskGroups[0].color?.let {
            viewModel.setBackgroundColor(it)
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.taskGroupList)
        binding.taskGroupList.apply {
            // TODO: 10-06-2021 15:21 Peeking value is not the same as the padding of the texts above
            addItemDecoration(PeekingItemDecoration(0.2))

            adapter = TaskGroupsAdapter(SampleData.taskGroups) {
                navigateToDetails(it)
            }

            addOnScrollListener(SnapScrollListener(snapHelper) { position ->
                viewModel.setBackgroundColor(
                    SampleData.taskGroups[position].color ?: R.color.default_background_color
                )
            })

            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        viewModel.backgroundColor.observe(viewLifecycleOwner, { color ->
            setBackgroundColor(color ?: R.color.default_background_color, true)
        })

    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(binding.toolbar.toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    private fun setBackgroundColor(@ColorRes color: Int, animate: Boolean = false) {
        val newColor = requireContext().getColor(color)
        val view = binding.root

        // XXX animate while scrolling?
        // TODO move to separate method
        if (animate) {
            // BackgroundColor does not have a getter so we have to get the current
            // color ourselves
            val currentColor =
                (view.background as? ColorDrawable)?.color ?: newColor
            ObjectAnimator.ofArgb(
                view,
                "backgroundColor",
                currentColor,
                newColor
            ).start()
        } else {
            view.setBackgroundColor(newColor)
        }
    }

    // FIXME: 25-05-2021 11:38 In the shared element transition the corners jump from the rounded corners of the cardView to
    //  the hard corner of the detail view. This does not work with the simple solution we're working with right now?
    // FIXME: 25-05-2021 11:42 On returning the list in the detail view just disappears. This looks ugly. Can we use a transition
    //  on this list?
    private fun navigateToDetails(taskTransitionData: TaskTransitionData) {
        val action =
            TaskGroupFragmentDirections.showTaskGroupDetails(taskTransitionData.position)

        findNavController().navigate(
            action,
            FragmentNavigatorExtras(*taskTransitionData.sharedElements)
        )
//        val ofFloat = ObjectAnimator.ofFloat(binding.toolbar.toolbar, "translationY", 200f).apply {
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationStart(animation: Animator?) {
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                    println("TaskGroupFragment.onAnimationCancel")
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    println("TaskGroupFragment.onAnimationEnd")
//                }
//
//                override fun onAnimationRepeat(animation: Animator?) {
//                    println("TaskGroupFragment.onAnimationRepeat")
//                }
//            })
//            duration = 120
//            startDelay = 180
//        }
//        ofFloat.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_group_menu, menu)
    }
}

