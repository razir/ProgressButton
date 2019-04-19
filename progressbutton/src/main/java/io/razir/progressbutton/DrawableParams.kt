package io.razir.progressbutton

import android.support.annotation.DimenRes
import android.support.annotation.StringRes

open class DrawableParams {
    @StringRes
    var buttonTextRes: Int? = null
    var buttonText: String? = null

    var gravity: Int = DrawableButton.GRAVITY_TEXT_END

    @DimenRes
    var textMarginRes: Int? = null
    var textMarginPx: Int = DrawableButton.DEFAULT
}