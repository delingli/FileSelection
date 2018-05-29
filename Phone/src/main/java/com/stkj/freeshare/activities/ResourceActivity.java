package com.stkj.freeshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.stkj.freeshare.R;

public final class ResourceActivity extends TitleBarActivity {
    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(R.string.title_resource);
        setContentView(R.layout.activity_resource);
    }

    public final void onClickCancel(View view) {

    }

    public final void onClickAccept(View view) {
        startActivity(new Intent(this, SendActivity.class));
        finish();
    }
}