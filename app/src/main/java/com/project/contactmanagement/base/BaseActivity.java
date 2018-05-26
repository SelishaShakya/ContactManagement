package com.project.contactmanagement.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.project.contactmanagement.application.MainApplication;
import com.project.contactmanagement.application.component.AppComponent;

import butterknife.ButterKnife;

/**
 * Created by Own on 4/10/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        init();
    }
    protected abstract int getLayout();
    protected abstract void init();

}
