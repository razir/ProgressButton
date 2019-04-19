package io.razir.progressbutton

import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.widget.TextView

/**
 * Java back support class to show the drawable. If you use kotlin please consider to use extensions
 * @see TextView.showDrawable
 */
class DrawableButtonUtils {

    companion object {

        /**
         *   Shows your animated drawable on the button with defined params.
         *   Important: drawable bounds should be defined already (eg. drawable.setBounds)
         *
         *   If params are not defined uses the default one.
         *
         *   The example of usage:
         *
         *   DrawableButtonUtils.showDrawable(button,yourDrawable,new DrawableParams())
         *
         *   If you want to continue using your button after showing the progress,
         *   please hide the progress and clean up resources by calling:
         *   @see hideDrawable
         *
         *   @param view to show the drawable
         *   @param drawable your animated drawable. Will be played automatically
         *   @param params use to set the text,position and margin
         */
        @JvmStatic
        fun showDrawable(
            view: TextView,
            drawable: Drawable,
            params: DrawableParams
        ) = view.showDrawable(drawable, params)

        /**
         * @return true if drawable is currently showing and false if not
         */
        @JvmStatic
        fun isDrawableActive(textView: TextView) = textView.isDrawableActive()

        /**
         * Hides the progress and clean up internal references
         * This method is required to call if you want to continue using your button
         * @param newText String value to show after hiding the progress
         */
        @JvmStatic
        fun hideDrawable(view: TextView, newText: String?) = view.hideDrawable(newText)

        /**
         * Hides the progress and clean up internal references
         * This method is required to call if you want to continue using your button
         * @param newTextRes String resource to show after hiding the progress
         */
        @JvmStatic
        fun hideDrawable(view: TextView, @StringRes newTextRes: Int) = view.hideDrawable(newTextRes)
    }
}