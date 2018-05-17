package com.ivy.ivyapp.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preContentView();
        setContentView(getContentView());
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    protected void loadData() {
    }

    protected void preContentView() {
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected void enterActivityFinishWithNoParams(Class clz) {
        startActivity(new Intent(this, clz));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
