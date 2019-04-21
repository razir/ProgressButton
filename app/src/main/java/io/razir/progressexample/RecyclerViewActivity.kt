package io.razir.progressexample

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.razir.progressbutton.*
import kotlinx.android.synthetic.main.activity_rv.*
import kotlinx.android.synthetic.main.item_button.view.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerViewActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)
        rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ButtonsAdapter(this@RecyclerViewActivity)
        }
    }
}


class ButtonsAdapter(private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<ButtonsAdapter.Holder>() {

    var inProgress = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = 100

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                buttonProgress.attachTextChangeAnimator()
                lifecycleOwner.bindProgressButton(buttonProgress)
                buttonProgress.setOnClickListener {
                    inProgress.add(adapterPosition)
                    buttonProgress.showProgress {
                        progressColor = Color.WHITE
                    }
                }
            }
        }

        fun bind(position: Int) {

            itemView.apply {
                number.text = "position #$position"
                buttonProgress.cleanUpDrawable()
                if (!inProgress.contains(position)) {
                    buttonProgress.setText(R.string.submit)
                } else {
                    buttonProgress.showProgress {
                        progressColor = Color.WHITE
                    }
                }
            }
        }
    }
}