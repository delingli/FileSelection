package com.stkj.freeshare.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;

import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.ActivitySplashBinding;
import com.stkj.freeshare.framework.Spi;

public final class SplashActivity extends BaseActivity {
    private ActivitySplashBinding mBinding;

    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View decor = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            decor.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            });
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        else
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        checkPermission(0x250, Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public final void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected final void onPermissionsGranted(final int code) {
        if (Spi.get(this).isNeedShowProtocol()) {
            mBinding.setIsNeedShowProtocol(true);
            mBinding.splashProtocolTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        } else {
            // TODO 添加开屏广告
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    jumpToMain();
                }
            }, 3000);
        }
    }

    private void jumpToMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    public final void onClickCancel(View view) {
        finish();
    }

    public final void onClickEnsure(View view) {
        Spi.get(this).setNeedShowProtocol(false);
        jumpToMain();
    }
}