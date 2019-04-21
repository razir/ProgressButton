package io.razir.progressexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openProgressButtons.setOnClickListener {
            startActivity(ProgressButtonsActivity.getStartIntent(this))
        }

        openDrawableButtons.setOnClickListener {
            startActivity(DrawableButtonsActivity.getStartIntent(this))
        }

        openRecyclerView.setOnClickListener {
            startActivity(RecyclerViewActivity.getStartIntent(this))
        }
    }
}