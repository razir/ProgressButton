package com.github.razir.progressexample.java;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.razir.progressbutton.ButtonTextAnimatorExtensionsKt;
import com.github.razir.progressbutton.DrawableButtonExtensionsKt;
import com.github.razir.progressbutton.DrawableParams;
import com.github.razir.progressbutton.ProgressButtonHolderKt;
import com.github.razir.progressbutton.ProgressParams;
import com.github.razir.progressexample.R;
import com.google.android.material.button.MaterialButton;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DrawableButtonsActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DrawableButtonsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_buttons);
        final MaterialButton buttonAnimatedDrawable = findViewById(R.id.buttonAnimatedDrawable);
        final MaterialButton buttonProgressMixed = findViewById(R.id.buttonProgressMixed);

        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonAnimatedDrawable);
        ProgressButtonHolderKt.bindProgressButton(this, buttonAnimatedDrawable);

        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgressMixed);
        ProgressButtonHolderKt.bindProgressButton(this, buttonProgressMixed);

        buttonProgressMixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMixed(buttonProgressMixed);
            }
        });
        buttonAnimatedDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimatedDrawable(buttonAnimatedDrawable);
            }
        });
    }

    private void showMixed(final Button button) {
        final Drawable animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check);

        //Defined bounds are required for your drawable
        int drawableSize = getResources().getDimensionPixelSize(R.dimen.doneSize);
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize);

        DrawableButtonExtensionsKt.showProgress(button, new Function1<ProgressParams, Unit>() {
            @Override
            public Unit invoke(ProgressParams progressParams) {
                progressParams.setButtonTextRes(R.string.loading);
                progressParams.setProgressColor(Color.WHITE);
                return Unit.INSTANCE;
            }
        });

        button.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
                DrawableButtonExtensionsKt.showDrawable(button, animatedDrawable, new Function1<DrawableParams, Unit>() {
                    @Override
                    public Unit invoke(DrawableParams drawableParams) {
                        drawableParams.setButtonTextRes(R.string.saved);
                        return Unit.INSTANCE;
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DrawableButtonExtensionsKt.hideDrawable(button, R.string.mixedBehaviour);
                    }
                }, 2000);
            }
        }, 3000);
    }

    private void showAnimatedDrawable(final Button button) {
        Drawable animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check);

        //Defined bounds are required for your drawable
        int drawableSize = getResources().getDimensionPixelSize(R.dimen.doneSize);
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize);
        button.setEnabled(false);

        DrawableButtonExtensionsKt.showDrawable(button, animatedDrawable, new Function1<DrawableParams, Unit>() {
            @Override
            public Unit invoke(DrawableParams drawableParams) {
                drawableParams.setButtonTextRes(R.string.saved);
                drawableParams.setTextMarginRes(R.dimen.drawableTextMargin);
                return Unit.INSTANCE;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DrawableButtonExtensionsKt.hideDrawable(button, R.string.animatedDrawable);
                button.setEnabled(true);
            }
        }, 3000);
    }
}
