package io.razir.progressbutton

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes

class TextChangeAnimatorParams {
    var useCurrentTextColor: Boolean = true

    @ColorInt
    var textColor: Int = 0

    var textColorList: ColorStateList? = null

    @ColorRes
    var textColorRes: Int? = null

    var fadeInMills = 150L
    var fadeOutMills = 150L
}