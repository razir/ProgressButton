package com.github.razir.progressbutton

import android.widget.TextView
import androidx.annotation.StringRes

/**
 * Java back support class to show the progress. If you use kotlin please consider to use extensions
 * @see TextView.showProgress
 */
class ProgressButtonUtils {

    companion object {

        /**
         *   Shows your progress on the button with defined params.
         *   If params are not defined uses the default one.
         *
         *   The example of usage:
         *
         *   ProgressButtonUtils.showProgress(button,new ProgressParams())
         *
         *   If you want to continue using your button after showing the progress,
         *   please hide the progress and clean up resources by calling:
         *   @see hideProgress
         *
         *   @param view to show the progress
         *   @param drawable your animated drawable. Will be played automatically
         *   @param params use to set the text,position and margin
         */
        @JvmStatic
        fun showProgress(
            textView: TextView,
            progressParams: ProgressParams
        ) = textView.showProgress(progressParams)

        /**
         * @return true if progress is currently showing and false if not
         */
        @JvmStatic
        fun isProgressActive(textView: TextView) = textView.isProgressActive()

        /**
         * Hides the progress and clean up internal references
         * This method is required to call if you want to continue using your button
         * @param newText String value to show after hiding the progress
         */
        @JvmStatic
        fun hideProgress(textView: TextView, newText: String?) = textView.hideProgress(newText)

        /**
         * Hides the progress and clean up internal references
         * This method is required to call if you want to continue using your button
         * @param newTextRes String resource to show after hiding the progress
         */
        @JvmStatic
        fun hideProgress(textView: TextView, @StringRes newTextRes: Int) = textView.hideProgress(newTextRes)
    }
}