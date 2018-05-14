package com.ivy.ivyapp.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivy.commonlibrary.utils.L;

import butterknife.ButterKnife;

/**
 * Created by Ivy on 2018/5/11.
 */

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
        L.d(getClass().getSimpleName() + " 构造函数...");
    }

    protected View mFragmentRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        L.d(getClass().getSimpleName() + " onCreateView");


        mFragmentRootView = inflater.inflate(getContentView(), null);
        ButterKnife.bind(this, mFragmentRootView);
        initView();
        return mFragmentRootView;
    }

    protected abstract void initView();

    protected abstract int getContentView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.d(getClass().getSimpleName() + " onActivityCreated");
        loadData();
    }

    protected void loadData() {
    }

}
