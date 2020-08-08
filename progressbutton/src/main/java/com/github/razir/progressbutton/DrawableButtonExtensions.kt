package com.github.razir.progressbutton

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.text.AllCapsTransformationMethod
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

/**
 *   Shows progress on the button with defined params.
 *   If params are not defined uses the default one.
 *
 *   The example of usage
 *
 *   button.showProgress { buttonText = "Loading", progressColor = Color.WHITE }
 *
 *   If you want to continue using your button after showing the progress,
 *   please hide the progress and clean up resources by calling:
 *   @see TextView.hideProgress
 *
 *   @receiver button to show the progress
 *   @param params use to set the text,position and customize the progress look
 */
@JvmOverloads
fun TextView.showProgress(params: ProgressParams.() -> Unit = {}) {
    val paramValues = ProgressParams()
    paramValues.params()
    showProgress(paramValues)
}

/**
 *   Shows your animated drawable on the button with defined params.
 *   Important: drawable bounds should be defined already (eg. drawable.setBounds)
 *   If params are not defined uses the default one.
 *
 *   The example of usage:
 *
 *   button.showDrawable(yourDrawable) { buttonText = "Done" }
 *
 *   If you want to continue using your button after showing the drawable,
 *   please hide the drawable and clean up resources by calling:
 *   @see TextView.hideDrawable
 *
 *   @receiver button to show the drawable
 *   @param drawable your animated drawable. Will be played automatically
 *   @param params use to set the text,position and margin
 */
@JvmOverloads
fun TextView.showDrawable(
    drawable: Drawable,
    params: DrawableParams.() -> Unit = {}
) {
    val paramValues = DrawableParams()
    paramValues.params()
    showDrawable(drawable, paramValues)
}

/**
 * @return true if progress is currently showing and false if not
 */
fun TextView.isProgressActive() = isDrawableActive()

/**
 * @return true if drawable is currently showing and false if not
 */
fun TextView.isDrawableActive(): Boolean {
    return activeViews.contains(this)
}

/**
 * Hides the progress and clean up internal references
 * This method is required to call if you want to continue using your button
 * @param newText String value to show after hiding the progress
 */
@JvmOverloads
fun TextView.hideProgress(newText: String? = null) = hideDrawable(newText)

/**
 * Hides the progress and clean up internal references
 * This method is required to call if you want to continue using your button
 * @param newTextRes String resource to show after hiding the progress
 */
fun TextView.hideProgress(@StringRes newTextRes: Int) = hideDrawable(newTextRes)

/**
 * Hides the progress and clean up internal references
 * This method is required to call if you want to continue using your button
 * @param newText String value to show after hiding the progress
 */
@JvmOverloads
fun TextView.hideDrawable(newText: String? = null) {
    cleanUpDrawable()
    if (isAnimatorAttached()) {
        animateTextChange(newText)
    } else {
        this.text = newText
    }
}

/**
 * Hides the drawable and clean up internal references
 * This method is required to call if you want to continue using your button
 * @param newTextRes String resource to show after hiding the progress
 */
fun TextView.hideDrawable(@StringRes newTextRes: Int) {
    hideDrawable(context.getString(newTextRes))
}


/**
 *   Shows progress on button.
 *   [Java back support version]
 */
internal fun TextView.showProgress(params: ProgressParams) {
    params.apply {
        val res = context.resources
        val progressStrokeValue = progressStrokeRes?.let { res.getDimensionPixelSize(it) } ?: progressStrokePx
        val progressRadiusValue = progressRadiusRes?.let { res.getDimensionPixelSize(it) } ?: progressRadiusPx
        val colors = when {
            progressColorRes != null -> intArrayOf(ContextCompat.getColor(context, progressColorRes!!))
            progressColor != null -> intArrayOf(progressColor!!)
            progressColors != null -> progressColors!!
            else -> intArrayOf()
        }
        val progressDrawable = generateProgressDrawable(context, colors, progressRadiusValue, progressStrokeValue)
        showDrawable(progressDrawable, params)
    }
}

/*
    Shows any animated drawable on button.
    [Java back support version]
 */
internal fun TextView.showDrawable(
    drawable: Drawable,
    paramValues: DrawableParams
) {
    paramValues.apply {
        val res = context.resources
        val buttonTextValue = buttonTextRes?.let { context.getString(it) } ?: buttonText
        val textMarginValue = textMarginRes?.let { res.getDimensionPixelSize(it) } ?: textMarginPx
        showDrawable(drawable, buttonTextValue, gravity, textMarginValue)
    }
}

private fun TextView.showDrawable(
    drawable: Drawable,
    text: String?,
    gravity: Int,
    textMarginPx: Int
) {
    if (isDrawableActive()) {
        cleanUpDrawable()
    }
    // Workaround to check if textAllCaps==true on any android api version
    if (transformationMethod?.javaClass?.name == "android.text.method.AllCapsTransformationMethod" ||
        transformationMethod is AllCapsTransformationMethod
    ) {
        transformationMethod = AllCapsSpannedTransformationMethod(context)
    }

    val drawableMargin = if (textMarginPx == DrawableButton.DEFAULT) {
        context.dpToPixels(DEFAULT_DRAWABLE_MARGIN_DP)
    } else {
        textMarginPx
    }
    val animatorAttached = isAnimatorAttached()
    val newText = getDrawableSpannable(drawable, text, gravity, drawableMargin, animatorAttached)
    if (animatorAttached) {
        animateTextChange(newText)
    } else {
        this.text = newText
    }

    addDrawableAttachViewListener()
    setupDrawableCallback(this, drawable)
}

private fun setupDrawableCallback(textView: TextView, drawable: Drawable) {
    val callback = object : Drawable.Callback {
        override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        }

        override fun invalidateDrawable(who: Drawable) {
            textView.invalidate()
        }

        override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        }
    }
    activeViews[textView] = DrawableViewData(drawable, callback)
    drawable.callback = callback
    if (drawable is Animatable) {
        drawable.start()
    }
}

private fun generateProgressDrawable(
    context: Context,
    progressColors: IntArray,
    progressRadiusPx: Int,
    progressStrokePx: Int

): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        setStyle(CircularProgressDrawable.DEFAULT)

        if (progressColors.isNotEmpty()) {
            setColorSchemeColors(*progressColors)
        }
        if (progressRadiusPx != DrawableButton.DEFAULT) {
            centerRadius = progressRadiusPx.toFloat()
        }
        if (progressStrokePx != DrawableButton.DEFAULT) {
            strokeWidth = progressStrokePx.toFloat()
        }
        val size = (centerRadius + strokeWidth).toInt() * 2
        setBounds(0, 0, size, size)
    }
}

private fun getDrawableSpannable(
    drawable: Drawable,
    text: String?,
    gravity: Int,
    drawableMarginPx: Int,
    useTextAlpha: Boolean
): SpannableString {
    val drawableSpan = DrawableSpan(drawable, useTextAlpha = useTextAlpha)
    return when (gravity) {
        DrawableButton.GRAVITY_TEXT_START -> {
            drawableSpan.paddingEnd = drawableMarginPx
            SpannableString(" ${text ?: ""}").apply {
                setSpan(drawableSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        DrawableButton.GRAVITY_TEXT_END -> {
            drawableSpan.paddingStart = drawableMarginPx
            SpannableString("${text ?: ""} ").apply {
                setSpan(drawableSpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        DrawableButton.GRAVITY_CENTER -> {
            SpannableString(" ").apply {
                setSpan(drawableSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        else -> throw IllegalArgumentException("Please set the correct gravity")
    }
}

internal data class DrawableViewData(var drawable: Drawable, val callback: Drawable.Callback)

private const val DEFAULT_DRAWABLE_MARGIN_DP = 10f

private fun Context.dpToPixels(dpValue: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics).toInt()