package dev.bandb.designstudio.design1.transition

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.transition.TransitionValues
import androidx.transition.Visibility

class Keep: Visibility {

    constructor(): super()
    constructor( @NonNull context: Context, @NonNull attrs: AttributeSet) :super(context, attrs)

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        return ValueAnimator.ofFloat(0f)
    }

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        return ValueAnimator.ofFloat(0f)
    }


}