package io.razir.progressbutton

import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes

open class ProgressParams : DrawableParams() {
    @DimenRes
    var progressRadiusRes: Int? = null
    var progressRadiusPx: Int = DrawableButton.DEFAULT

    @DimenRes
    var progressStrokeRes: Int? = null
    var progressStrokePx: Int = DrawableButton.DEFAULT

    @ColorInt
    var progressColor: Int? = null
    @ColorRes
    var progressColorRes: Int? = null
    var progressColors: IntArray? = null
}