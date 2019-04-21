package io.razir.progressexample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.razir.progressbutton.*
import kotlinx.android.synthetic.main.activity_progress_buttons.*

class ProgressButtonsActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProgressButtonsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_buttons)

        bindProgressButton(buttonProgressRightText)
        bindProgressButton(buttonProgressLeftText)
        bindProgressButton(buttonProgressCenter)
        bindProgressButton(buttonProgressCustomStyle)


        buttonProgressRightText.attachTextChangeAnimator()
        buttonProgressLeftText.attachTextChangeAnimator()
        buttonProgressCustomStyle.attachTextChangeAnimator()
        buttonProgressCenter.attachTextChangeAnimator {
            fadeInMills = 300
            fadeOutMills = 300
        }

        buttonProgressRightText.setOnClickListener {
            showProgressRight(buttonProgressRightText)
        }
        buttonProgressLeftText.setOnClickListener {
            showProgressLeft(buttonProgressLeftText)
        }
        buttonProgressCenter.setOnClickListener {
            showProgressCenter(buttonProgressCenter)
        }
        buttonProgressCustomStyle.setOnClickListener {
            showProgressCustom(buttonProgressCustomStyle)
        }

    }

    private fun showProgressRight(button: Button) {
        button.showProgress {
            buttonTextRes = R.string.loading
            progressColor = Color.WHITE
        }
        button.isEnabled = false
        Handler().postDelayed({
            button.isEnabled = true
            button.hideProgress(R.string.progressRight)
        }, 3000)
    }

    private fun showProgressLeft(button: Button) {
        button.showProgress {
            buttonTextRes = R.string.loading
            progressColor = Color.WHITE
            gravity = DrawableButton.GRAVITY_TEXT_START
        }

        button.isEnabled = false
        Handler().postDelayed({
            button.isEnabled = true
            button.hideProgress(R.string.progressLeft)
        }, 3000)
    }

    private fun showProgressCenter(button: Button) {
        button.showProgress {
            progressColor = Color.WHITE
            gravity = DrawableButton.GRAVITY_CENTER
        }

        button.isEnabled = false
        Handler().postDelayed({
            button.isEnabled = true
            button.hideProgress(R.string.progressCenter)
        }, 3000)
    }

    private fun showProgressCustom(button: Button) {
        button.showProgress {
            buttonTextRes = R.string.loading
            progressColors = intArrayOf(Color.WHITE, Color.MAGENTA, Color.GREEN)
            gravity = DrawableButton.GRAVITY_TEXT_END
            progressRadiusRes = R.dimen.progressRadius
            progressStrokeRes = R.dimen.progressStroke
            textMarginRes = R.dimen.textMarginStyled
        }
        button.isEnabled = false
        Handler().postDelayed({
            button.isEnabled = true
            button.hideProgress(R.string.progressCustomStyle)
        }, 5000)
    }
}

