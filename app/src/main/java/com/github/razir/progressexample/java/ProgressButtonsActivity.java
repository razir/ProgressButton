package com.github.razir.progressexample.java;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.razir.progressbutton.ButtonTextAnimatorExtensionsKt;
import com.github.razir.progressbutton.DrawableButton;
import com.github.razir.progressbutton.DrawableButtonExtensionsKt;
import com.github.razir.progressbutton.ProgressButtonHolderKt;
import com.github.razir.progressbutton.ProgressParams;
import com.github.razir.progressbutton.TextChangeAnimatorParams;
import com.github.razir.progressexample.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProgressButtonsActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProgressButtonsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_buttons);
        final Button buttonProgressRightText = findViewById(R.id.buttonProgressRightText);
        final Button buttonProgressLeftText = findViewById(R.id.buttonProgressLeftText);
        final Button buttonProgressCenter = findViewById(R.id.buttonProgressCenter);
        final Button buttonProgressCustomStyle = findViewById(R.id.buttonProgressCustomStyle);

        ProgressButtonHolderKt.bindProgressButton(this, buttonProgressRightText);
        ProgressButtonHolderKt.bindProgressButton(this, buttonProgressLeftText);
        ProgressButtonHolderKt.bindProgressButton(this, buttonProgressCenter);
        ProgressButtonHolderKt.bindProgressButton(this, buttonProgressCustomStyle);

        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgressRightText);
        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgressLeftText);
        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgressCustomStyle);
        ButtonTextAnimatorExtensionsKt.attachTextChangeAnimator(buttonProgressCenter, new Function1<TextChangeAnimatorParams, Unit>() {
            @Override
            public Unit invoke(TextChangeAnimatorParams textChangeAnimatorParams) {
                textChangeAnimatorParams.setFadeInMills(300);
                textChangeAnimatorParams.setFadeOutMills(300);
                return Unit.INSTANCE;
            }
        });

        buttonProgressRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressRight(buttonProgressRightText);
            }
        });
        buttonProgressLeftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressLeft(buttonProgressLeftText);
            }
        });
        buttonProgressCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressCenter(buttonProgressCenter);
            }
        });
        buttonProgressCustomStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressCustom(buttonProgressCustomStyle);
            }
        });
    }


    private void showProgressRight(final Button button) {
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
                DrawableButtonExtensionsKt.hideProgress(button, R.string.progressRight);
            }
        }, 3000);
    }

    private void showProgressLeft(final Button button) {
        DrawableButtonExtensionsKt.showProgress(button, new Function1<ProgressParams, Unit>() {
            @Override
            public Unit invoke(ProgressParams progressParams) {
                progressParams.setButtonTextRes(R.string.loading);
                progressParams.setProgressColor(Color.WHITE);
                progressParams.setGravity(DrawableButton.GRAVITY_TEXT_START);
                return Unit.INSTANCE;
            }
        });
        button.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
                DrawableButtonExtensionsKt.hideProgress(button, R.string.progressLeft);
            }
        }, 3000);
    }

    private void showProgressCenter(final Button button) {
        DrawableButtonExtensionsKt.showProgress(button, new Function1<ProgressParams, Unit>() {
            @Override
            public Unit invoke(ProgressParams progressParams) {
                progressParams.setProgressColor(Color.WHITE);
                progressParams.setGravity(DrawableButton.GRAVITY_CENTER);
                return Unit.INSTANCE;
            }
        });
        button.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
                DrawableButtonExtensionsKt.hideProgress(button, R.string.progressCenter);
            }
        }, 3000);
    }

    private void showProgressCustom(final Button button) {
        DrawableButtonExtensionsKt.showProgress(button, new Function1<ProgressParams, Unit>() {
            @Override
            public Unit invoke(ProgressParams progressParams) {
                progressParams.setButtonTextRes(R.string.loading);
                progressParams.setProgressColors(new int[] {Color.WHITE, Color.MAGENTA, Color.GREEN});
                progressParams.setGravity(DrawableButton.GRAVITY_TEXT_END);
                progressParams.setProgressRadiusRes(R.dimen.progressRadius);
                progressParams.setProgressStrokeRes(R.dimen.progressStroke);
                progressParams.setTextMarginRes(R.dimen.textMarginStyled);
                return Unit.INSTANCE;
            }
        });
        button.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
                DrawableButtonExtensionsKt.hideProgress(button, R.string.progressCustomStyle);
            }
        }, 5000);
    }
}
