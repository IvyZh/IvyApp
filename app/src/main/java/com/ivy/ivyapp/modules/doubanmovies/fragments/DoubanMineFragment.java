package com.ivy.ivyapp.modules.doubanmovies.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class DoubanMineFragment extends BaseFragment {

    @BindView(R.id.tl_tab)
    TabLayout mTlTab;
    @BindView(R.id.vp_pager)
    ViewPager mVpPager;
    private String[] tabTitles = {"讨论", "想看", "在看", "看过", "影评", "影人"};
    private List<BaseFragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_douban_mine;
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
        mFragmentList.add(new P1MineFragment());
        mFragmentList.add(new P2MineFragment());
        mFragmentList.add(new P3MineFragment());
        mFragmentList.add(new P4MineFragment());
        mFragmentList.add(new P5MineFragment());
        mFragmentList.add(new P6MineFragment());
    }


    @Override
    protected void loadData() {

    }


}
