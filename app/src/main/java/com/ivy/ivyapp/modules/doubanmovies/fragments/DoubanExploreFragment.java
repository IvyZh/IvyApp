package com.ivy.ivyapp.modules.doubanmovies.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class DoubanExploreFragment extends BaseFragment {

    @BindView(R.id.tl_tab)
    TabLayout mTlTab;
    private String[] tabTitles = {"电影", "电视"};
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private int mCurrPos = 0;
    private BaseFragment mCurrentFragment;

    @Override
    protected int getContentView() {
        return R.layout.fragment_douban_explore;
    }


    @Override
    protected void initView() {
        initFragments();

        mTlTab.addTab(mTlTab.newTab().setText(tabTitles[0]));
        mTlTab.addTab(mTlTab.newTab().setText(tabTitles[1]));

        mTlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                L.v("onTabSelected:" + tab.getPosition());
                switchFragment(mFragmentList.get(tab.getPosition()), tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragments() {
        mFragmentList.add(new MovieExploreFragment());
        mFragmentList.add(new TVExploreFragment());

//        mFragmentManager = getSupportFragmentManager();
        mFragmentManager = getChildFragmentManager();
//        mFragmentManager = getActivity().getChildFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_explore, mFragmentList.get(mCurrPos)).commit();
    }


    private void switchFragment(BaseFragment to, int toPos) {
        if (mCurrentFragment != to) {
            BaseFragment from = mFragmentList.get(mCurrPos);
            mCurrPos = toPos;
            mCurrentFragment = to;
            if (to.isAdded()) {//已经有了
                mFragmentManager.beginTransaction().hide(from).show(to).commit();
            } else {//第一次加进来
                mFragmentManager.beginTransaction().hide(from).add(R.id.fl_explore, to).commit();
            }
        }
    }

    @Override
    protected void loadData() {

    }


}
