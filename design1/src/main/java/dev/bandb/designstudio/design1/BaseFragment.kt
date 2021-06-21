package dev.bandb.designstudio.design1

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val posTop = statusBarInsets.top
            val posBottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

            // Note: CardView uses padding internally to offset the content for the shadows, so applying
            //       padding normally does not work. Instead we have to use its own contentPadding
            if (view is CardView) {
                view.setContentPadding(
                    view.contentPaddingLeft,
                    posTop,
                    view.contentPaddingRight,
                    posBottom
                )
            } else {
                view.updatePadding(top = posTop, bottom = posBottom)
            }

            view.setOnApplyWindowInsetsListener(null)
            insets
        }
    }



}