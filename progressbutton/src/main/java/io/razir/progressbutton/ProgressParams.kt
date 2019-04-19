package io.razir.progressbutton

import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes

/**
 * Use to customize your progress drawable
 * The final progress drawable size will be = (radius + stroke) * 2
 */
open class ProgressParams : DrawableParams() {

    /**
     * Dimension resource used for the progress radius
     * The default value is 11dp
     */
    @DimenRes
    var progressRadiusRes: Int? = null

    /**
     * Progress radius size in pixels
     * The default value is 11dp
     */
    var progressRadiusPx: Int = DrawableButton.DEFAULT

    /**
     * Dimension resource used for the progress stroke
     * The default value is 3dp
     */
    @DimenRes
    var progressStrokeRes: Int? = null

    /**
     * Progress stroke size in pixels
     * The default value is 3dp
     */
    var progressStrokePx: Int = DrawableButton.DEFAULT

    /**
     * Single color int value used for the progress
     */
    @ColorInt
    var progressColor: Int? = null

    /**
     * Single color resource value used for the progress
     */
    @ColorRes
    var progressColorRes: Int? = null

    /**
     * List of color int values used for the progress
     */
    var progressColors: IntArray? = null
}