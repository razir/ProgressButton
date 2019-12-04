package com.github.razir.progressbutton

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.text.SpannableString
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import kotlin.collections.ArrayList

/**
 * adds fade in/fade out animations on drawable/progress showing
 *
 * example: button.attachTextChangeAnimator { fadeInMills = 200 }
 *
 * @param params config for animations
 */
fun TextView.attachTextChangeAnimator(params: TextChangeAnimatorParams.() -> Unit = {}) {
    val paramValues = TextChangeAnimatorParams()
    paramValues.params()
    attachTextChangeAnimator(paramValues)
}

/**
 * adds fade in/fade out animations on drawable/progress showing
 * @param params config for animations
 */
fun TextView.attachTextChangeAnimator(params: TextChangeAnimatorParams?) {
    val animParams = params?.let { params } ?: TextChangeAnimatorParams()
    if (animParams.useCurrentTextColor) {
        animParams.textColorList = textColors
    } else {
        if (animParams.textColorRes != null) {
            animParams.textColor = ContextCompat.getColor(context, animParams.textColorRes!!)
        }
    }
    addTextAnimationAttachViewListener()
    attachedViews[this] = params
}

/**
 * remove support fade in/fade out animations on drawable/progress showing
 */
fun TextView.detachTextChangeAnimator() {
    if (attachedViews.containsKey(this)) {
        cancelAnimations()
        attachedViews.remove(this)
        removeTextAnimationAttachViewListener()
    }
}

/**
 * checks if animations handler is currently active for the given button
 */
fun TextView.isAnimatorAttached(): Boolean {
    return attachedViews.containsKey(this)
}

internal fun TextView.animateTextChange(newText: String?) {
    animateTextChange(newText?.let { SpannableString(newText) })
}

internal fun TextView.animateTextChange(newText: SpannableString?) {
    cancelAnimations()
    val params = attachedViews[this]!!
    val textColor = getAnimateTextColor()

    val fadeInAnim = ObjectAnimator.ofInt(this, "textColor", ColorUtils.setAlphaComponent(textColor, 0), textColor)
        .apply {
            duration = params.fadeInMills
            setEvaluator(ArgbEvaluator())
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    cleaAnimator(animation)
                    resetColor()
                }

                override fun onAnimationCancel(animation: Animator) {
                    resetColor()
                    cleaAnimator(animation)
                }

                override fun onAnimationStart(animation: Animator) {
                    addAnimator(animation)
                }
            })
            start()
        }

    val fadeOutAnim = ObjectAnimator.ofInt(this, "textColor", textColor, ColorUtils.setAlphaComponent(textColor, 0))
        .apply {
            duration = params.fadeOutMills
            setEvaluator(ArgbEvaluator())
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    text = newText
                    fadeInAnim.start()
                    cleaAnimator(animation)
                }

                override fun onAnimationCancel(animation: Animator) {
                    text = newText
                    resetColor()
                    cleaAnimator(animation)
                }

                override fun onAnimationStart(animation: Animator) {
                    addAnimator(animation)
                }
            })
        }
    fadeOutAnim.start()
}

private fun TextView.addAnimator(animator: Animator) {
    if (activeAnimations.containsKey(this)) {
        val animations = activeAnimations[this]
        animations?.add(animator)
    } else {
        activeAnimations[this] = mutableListOf(animator)
    }
}

private fun TextView.cleaAnimator(animator: Animator) {
    if (activeAnimations.containsKey(this)) {
        val animations = activeAnimations[this]!!
        animations.remove(animator)
        if (animations.isEmpty()) {
            activeAnimations.remove(this)
        }
    }
}

private fun TextView.resetColor() {
    if (isAnimatorAttached()) {
        val params = attachedViews[this]!!
        params.textColorList?.let {
            setTextColor(it)
        } ?: run {
            setTextColor(params.textColor)
        }
    }
}

internal fun TextView.cancelAnimations() {
    if (activeAnimations.containsKey(this)) {
        val animations = activeAnimations[this]!!
        val copy = ArrayList<Animator>(animations)
        copy.forEach {
            it.cancel()
        }
        activeAnimations.remove(this)
    }
}

private fun TextView.getAnimateTextColor(): Int {
    val params = attachedViews[this]!!
    return when {
        params.textColorList != null -> {
            val viewState = this.drawableState
            params.textColorList!!.getColorForState(viewState, Color.BLACK)
        }
        else -> {
            params.textColor
        }
    }
}


