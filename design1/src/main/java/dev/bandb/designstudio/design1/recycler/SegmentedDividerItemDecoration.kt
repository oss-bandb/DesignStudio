package dev.bandb.designstudio.design1.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import dev.bandb.designstudio.design1.TaskHeaderViewHolder
import kotlin.math.roundToInt

// TODO refactor and clean up
class SegmentedDividerItemDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {
    private val divider: Drawable
    private val bounds: Rect = Rect()

    init {
        val typedArray = context.obtainStyledAttributes(ATTRS)
        divider = typedArray.getDrawable(0)!!
        typedArray.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val right: Int = parent.width

        val itemCount = parent.childCount
        for (i in 0 until itemCount - 1) {
            val child = parent.getChildAt(i)
            val childViewHolder = parent.getChildViewHolder(child)

            if (childViewHolder is TaskHeaderViewHolder
                || child !is LinearLayout
            ) continue

            val childChild = child[1]
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + child.translationY.roundToInt()
            val top = bottom - divider.intrinsicHeight
            val left = childChild.left
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, 0, 0, divider.intrinsicHeight)
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}