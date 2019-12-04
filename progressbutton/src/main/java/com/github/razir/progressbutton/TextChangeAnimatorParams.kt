package com.github.razir.progressbutton

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

/**
 * progress/drawable button showing animation config
 * @see attachTextChangeAnimator
 */
class TextChangeAnimatorParams {

    /**
     * fade in /fade out using current color / color state
     * if you use ColorStateList the library use the current button state color
     */
    var useCurrentTextColor: Boolean = true

    /**
     * fade in /fade out color int (eg. Color.WHITE)
     */
    @ColorInt
    var textColor: Int = 0

    /**
     * fade in /fade out ColorStateList
     */
    var textColorList: ColorStateList? = null

    /**
     * fade in /fade out color res
     */
    @ColorRes
    var textColorRes: Int? = null

    /**
     * fade in animation time in mills
     */
    var fadeInMills = 150L

    /**
     * fade out animation time in mills
     */
    var fadeOutMills = 150L
}