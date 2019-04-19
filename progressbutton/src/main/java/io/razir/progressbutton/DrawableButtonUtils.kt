package io.razir.progressbutton

import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.widget.TextView

class DrawableButtonUtils {

    companion object {

        @JvmStatic
        fun showDrawable(
            textView: TextView,
            drawable: Drawable,
            drawableParams: DrawableParams
        ) = textView.showDrawable(drawable, drawableParams)

        @JvmStatic
        fun isDrawableActive(textView: TextView) = textView.isDrawableActive()

        @JvmStatic
        fun hideDrawable(textView: TextView, newText: String?) = textView.hideDrawable(newText)

        @JvmStatic
        fun hideDrawable(textView: TextView, @StringRes newTextRes: Int) = textView.hideDrawable(newTextRes)
    }
}