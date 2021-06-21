package dev.bandb.designstudio.design1.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class SnapScrollListener(
    private val snapHelper: SnapHelper,
    private val onSnapPositionChanged: (Int) -> Unit
) :
    RecyclerView.OnScrollListener() {

    private var lastSnapPosition = RecyclerView.NO_POSITION

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return

        val snapPosition = snapHelper.getCurrentSnapPosition(recyclerView)
        if (lastSnapPosition != snapPosition) {
            onSnapPositionChanged(snapPosition)
            lastSnapPosition = snapPosition
        }
    }
}

internal fun SnapHelper.getCurrentSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}