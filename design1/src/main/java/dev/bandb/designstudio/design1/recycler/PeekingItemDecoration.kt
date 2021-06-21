package dev.bandb.designstudio.design1.recycler

import android.graphics.Rect
import android.view.View
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

// TODO name not ideal
class PeekingItemDecoration(@FloatRange(from = 0.0, to =1.0) private val peekingPercentage: Double) : RecyclerView.ItemDecoration() {

    init {
        if (peekingPercentage !in 0.0..1.0) {
            throw IllegalArgumentException("peekingPercentage must be between 0.0 and 1.0 (inclusive)")
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        view.layoutParams.width = (parent.measuredWidth * (1.0 - peekingPercentage)).roundToInt()

        val offset = calculateOffset(parent, view)
        outRect.left = if (isPosition(view, parent, 0)) offset else offset / 4
        outRect.right = if (isPosition(view, parent, state.itemCount - 1)) offset else offset / 4
    }

    private fun calculateOffset(
        parent: RecyclerView,
        view: View
    ) = ((parent.measuredWidth - view.layoutParams.width) / 2.0).roundToInt()

    private fun isPosition(view: View, parent: RecyclerView, position: Int): Boolean {
        return parent.getChildLayoutPosition(view) == position
    }

}