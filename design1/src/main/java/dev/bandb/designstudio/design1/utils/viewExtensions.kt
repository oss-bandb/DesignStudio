package dev.bandb.designstudio.design1.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}