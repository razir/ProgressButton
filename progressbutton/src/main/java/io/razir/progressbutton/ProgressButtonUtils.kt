package io.razir.progressbutton

import android.support.annotation.StringRes
import android.widget.TextView

class ProgressButtonUtils {

    companion object {

        @JvmStatic
        fun showProgress(
            textView: TextView,
            progressParams: ProgressParams
        ) = textView.showProgress(progressParams)

        @JvmStatic
        fun isProgressActive(textView: TextView) = textView.isProgressActive()

        @JvmStatic
        fun hideProgress(textView: TextView, newText: String?) = textView.hideProgress(newText)

        @JvmStatic
        fun hideProgress(textView: TextView, @StringRes newTextRes: Int) = textView.hideProgress(newTextRes)
    }
}