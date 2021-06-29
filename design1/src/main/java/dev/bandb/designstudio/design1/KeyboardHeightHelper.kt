package dev.bandb.designstudio.design1

import android.app.Activity
import android.graphics.Rect
import android.view.View

class KeyboardHeightHelper(
    activity: Activity,
    activityRootView: View,
    listener: (Int) -> Unit
) {
    private val decorView: View = activity.window.decorView
    private var lastKeyboardHeight = -1
    private val keyboardHeight: Int
        get() {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            return decorView.height - rect.bottom
        }

    init {
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val keyboardHeight = keyboardHeight
            if (lastKeyboardHeight != keyboardHeight) {
                lastKeyboardHeight = keyboardHeight
                listener(keyboardHeight)
            }
        }
    }
}