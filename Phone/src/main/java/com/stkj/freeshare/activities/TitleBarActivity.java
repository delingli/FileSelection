package com.stkj.freeshare.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aigestudio.avatar.utils.SysUtil;
import com.stkj.freeshare.R;

abstract class TitleBarActivity extends BaseActivity {
    protected TextView mTVTitle;

    private Toolbar mToolbar;
    private FrameLayout mFLContent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.setContentView(R.layout.activity_title_bar);
        mFLContent = findViewById(R.id.tb_content_fl);
        mToolbar = findViewById(R.id.tool_bar);
        mTVTitle = findViewById(R.id.toolbar_title);
        View statusBar = findViewById(R.id.status_bar);
        View navBar = findViewById(R.id.nav_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            if (SysUtil.hasSoftNavBar(this))
                navBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public final void setContentView(final int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mFLContent);
    }
}