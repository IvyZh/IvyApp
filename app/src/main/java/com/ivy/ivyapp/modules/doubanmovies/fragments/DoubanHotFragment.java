package com.ivy.ivyapp.modules.doubanmovies.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class DoubanHotFragment extends BaseFragment {

    @BindView(R.id.tl_tab)
    TabLayout mTlTab;
    @BindView(R.id.vp_pager)
    ViewPager mVpPager;
    private String[] tabTitles = {"正在热映", "即将上映"};
    private List<BaseFragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_douban_hot;
    }


    @Override
    protected void initView() {
        initFragments();
        //这里注意的是，因为我是在fragment中创建MyFragmentStatePagerAdapter，所以要传getChildFragmentManager()
        mVpPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];

            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabTitles.length;
            }
        });
        mTlTab.setupWithViewPager(mVpPager);
        mTlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                L.v("onTabSelected:" + tab.getPosition());
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
        mFragmentList.add(new InTheatersHotFragment());
        mFragmentList.add(new ComingSoonHotFragment());
//        mFragmentList.add(new ComingSoonHotFragment());
    }


    @Override
    protected void loadData() {

    }


}
