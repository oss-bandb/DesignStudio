package dev.bandb.designstudio.design1.utils.transition

import android.view.View

data class TaskTransitionData(val position: Int, val sharedElements: Array<Pair<View, String>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskTransitionData

        if (position != other.position) return false
        if (!sharedElements.contentEquals(other.sharedElements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position
        result = 31 * result + sharedElements.contentHashCode()
        return result
    }
}