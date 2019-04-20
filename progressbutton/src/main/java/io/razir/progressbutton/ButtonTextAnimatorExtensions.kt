package io.razir.progressbutton

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.widget.TextView
import kotlin.collections.ArrayList

fun TextView.attachTextChangeAnimator(params: TextChangeAnimatorParams.() -> Unit = {}) {
    val paramValues = TextChangeAnimatorParams()
    paramValues.params()
    attachTextChangeAnimator(paramValues)
}

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

fun TextView.detachTextChangeAnimator() {
    if (attachedViews.containsKey(this)) {
        cancelAnimations()
        attachedViews.remove(this)
        removeTextAnimationAttachViewListener()
    }
}

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

    val fadeInAnim = ObjectAnimator.ofInt(this, "textColor", Color.TRANSPARENT, textColor)
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

    val fadeOutAnim = ObjectAnimator.ofInt(this, "textColor", textColor, Color.TRANSPARENT)
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


