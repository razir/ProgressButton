package io.razir.progressexample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.razir.progressbutton.*
import kotlinx.android.synthetic.main.activity_drawable_buttons.*

class DrawableButtonsActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DrawableButtonsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_buttons)


        buttonAnimatedDrawable.attachTextChangeAnimator()
        bindProgressButton(buttonAnimatedDrawable)

        buttonProgressMixed.attachTextChangeAnimator()
        bindProgressButton(buttonProgressMixed)

        buttonProgressMixed.setOnClickListener {
            showMixed(buttonProgressMixed)
        }
        buttonAnimatedDrawable.setOnClickListener {
            showAnimatedDrawable(buttonAnimatedDrawable)
        }
    }

    private fun showMixed(button: Button) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.doneSize)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        button.showProgress {
            buttonTextRes = R.string.loading
            progressColor = Color.WHITE
        }
        button.isEnabled = false


        Handler().postDelayed({
            button.isEnabled = true

            button.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.saved
            }
            Handler().postDelayed({
                button.hideDrawable(R.string.mixedBehaviour)
            }, 2000)
        }, 3000)
    }

    private fun showAnimatedDrawable(button: Button) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.doneSize)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        button.isEnabled = false

        button.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.saved
            textMarginRes = R.dimen.drawableTextMargin
        }

        Handler().postDelayed({
            button.hideDrawable(R.string.animatedDrawable)
            button.isEnabled = true
        }, 3000)
    }
}