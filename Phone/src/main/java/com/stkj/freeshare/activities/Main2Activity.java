package com.stkj.freeshare.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stkj.freeshare.R;
import com.stkj.freeshare.fragments.ResourceFileFragment;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ResourceFileFragment fileFragment = (ResourceFileFragment) ResourceFileFragment.newInstance();
        transaction.replace(R.id.fl_content, fileFragment);
        transaction.commit();
    }
}
