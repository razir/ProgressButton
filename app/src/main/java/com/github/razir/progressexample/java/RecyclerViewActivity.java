package com.github.razir.progressexample.java;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.razir.progressbutton.ButtonTextAnimatorExtensionsKt;
import com.github.razir.progressbutton.DrawableButtonExtensionsKt;
import com.github.razir.progressbutton.ProgressButtonHolderKt;
import com.github.razir.progressbutton.ProgressParams;
import com.github.razir.progressexample.R;
import com.google.android.material.button.MaterialButton;

import java.util.HashSet;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class RecyclerViewActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, RecyclerViewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rv);
        RecyclerView rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ButtonsAdapter(this));
    }

    static class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.Holder>{
        private final LifecycleOwner lifecycleOwner;
        private final Set<Integer> inProgress = new HashSet<>();

        public ButtonsAdapter(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class Holder extends RecyclerView.ViewHolder {

            private MaterialButton buttonProgress;
            private TextView number;

            public Holder(@NonNull View itemView) {
                super(itemView);
                buttonProgress = itemView.findViewById(R.id.buttonProgress);
                number = itemView.findViewById(R.id.number);
                ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgress);
                ProgressButtonHolderKt.bindProgressButton(lifecycleOwner, buttonProgress);
                buttonProgress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inProgress.add(getAdapterPosition());
                        DrawableButtonExtensionsKt.showProgress(buttonProgress, new Function1<ProgressParams, Unit>() {
                            @Override
                            public Unit invoke(ProgressParams progressParams) {
                                progressParams.setProgressColor(Color.WHITE);
                                return Unit.INSTANCE;
                            }
                        });
                    }
                });
            }

            public void bind(int position) {
                number.setText("position #" + position);
                ProgressButtonHolderKt.cleanUpDrawable(buttonProgress);
                if (!inProgress.contains(position)) {
                    buttonProgress.setText(R.string.submit);
                } else {
                    DrawableButtonExtensionsKt.showProgress(buttonProgress, new Function1<ProgressParams, Unit>() {
                        @Override
                        public Unit invoke(ProgressParams progressParams) {
                            progressParams.setProgressColor(Color.WHITE);
                            return Unit.INSTANCE;
                        }
                    });
                }
            }
        }
    }
}
