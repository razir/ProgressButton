package io.razir.progressbutton

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import android.text.SpannableString
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import java.util.*

private val activeViews = WeakHashMap<TextView, DrawableViewData>()

private data class DrawableViewData(var drawable: Drawable, val callback: Drawable.Callback)

private const val DEFAULT_DRAWABLE_MARGIN_DP = 10f

fun TextView.showProgress(params: ProgressParams.() -> Unit) {
    val paramValues = ProgressParams()
    paramValues.params()
    showProgress(paramValues)
}

fun TextView.showProgress(params: ProgressParams) {
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

fun TextView.showDrawable(
    drawable: Drawable,
    params: DrawableParams.() -> Unit
) {
    val paramValues = DrawableParams()
    paramValues.params()
    showDrawable(drawable, paramValues)
}

fun TextView.showDrawable(
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
    val drawableMargin = if (textMarginPx == DrawableButton.DEFAULT) {
        context.dpToPixels(DEFAULT_DRAWABLE_MARGIN_DP)
    } else {
        textMarginPx
    }
    this.text = getDrawableSpannable(drawable, text, gravity, drawableMargin)

    addViewListener(this)
    setupDrawableCallback(this, drawable)
    if (drawable is Animatable) {
        drawable.start()
    }
}


fun TextView.isProgressActive() = isDrawableActive()

fun TextView.isDrawableActive(): Boolean {
    return activeViews.contains(this)
}

fun TextView.hideProgress(newText: String? = null) = hideDrawable(newText)

fun TextView.hideProgress(@StringRes newTextRes: Int) = hideDrawable(newTextRes)

fun TextView.hideDrawable(newText: String? = null) {
    cleanUpDrawable()
    this.text = newText
}

fun TextView.hideDrawable(@StringRes newTextRes: Int) {
    hideDrawable(context.getString(newTextRes))
}

private fun addViewListener(textView: TextView) {
    textView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            if (activeViews.containsKey(v)) {
                activeViews[v]?.drawable?.apply {
                    if (this is Animatable) {
                        stop()
                    }
                }
                activeViews.remove(v)
            }
            v?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) {
        }
    })
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
    drawableMarginPx: Int
): SpannableString {
    val drawableSpan = DrawableSpan(drawable)
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

private fun TextView.cleanUpDrawable() {
    if (activeViews.containsKey(this)) {
        activeViews[this]?.drawable?.apply {
            if (this is Animatable) {
                stop()
            }
            callback = null
        }
        activeViews.remove(this)
    }
}

private fun Context.dpToPixels(dpValue: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics).toInt()