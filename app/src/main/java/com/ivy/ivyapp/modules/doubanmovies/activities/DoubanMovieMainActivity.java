package com.ivy.ivyapp.modules.doubanmovies.activities;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.doubanmovies.fragments.DoubanExploreFragment;
import com.ivy.ivyapp.modules.doubanmovies.fragments.DoubanHotFragment;
import com.ivy.ivyapp.modules.doubanmovies.fragments.DoubanMineFragment;
import com.ivy.ivyapp.utils.L;
import com.ivy.ivyapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivy on 2018/5/11.
 */

public class DoubanMovieMainActivity extends BaseActivity {


    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    private int mCurrPos = 0;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();

    @Override
    protected int getContentView() {
        return R.layout.activity_doubanmovie_main;
    }

    @Override
    protected void initView() {
        initBottomNavigation();
        initFragments();
    }


    private void initBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(UIUtils.getString(R.string.tab_hot), R.drawable.ic_hot_normal);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(UIUtils.getString(R.string.tab_exolore), R.drawable.ic_explore_normal);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(UIUtils.getString(R.string.tab_mine), R.drawable.ic_mine_normal);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);
        // Enable the translation of the FloatingActionButton
        // bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

        // Change colors
//        bottomNavigation.setAccentColor(Color.BLUE);//标签激活(选中时的颜色)
//        bottomNavigation.setInactiveColor(Color.RED);//未被激活的颜色
        bottomNavigation.setAccentColor(Color.parseColor("#494949"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Display color under navigation bar (API 21+)
        // Don't forget these lines in your style-v21
        // <item name="android:windowTranslucentNavigation">true</item>
        // <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(false);

        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        // bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        //        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        // Add or remove notification for each item
        //        bottomNavigation.setNotification("1", 1);
        // OR
        //        AHNotification notification = new AHNotification.Builder()
        //                .setText("1")
        //                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_back))
        //                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_text))
        //                .build();
        //        bottomNavigation.setNotification(notification, 1);

        //Enable / disable item & set disable color
        bottomNavigation.enableItemAtPosition(2);
//        bottomNavigation.disableItemAtPosition(2);
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                L.v("setOnTabSelectedListener:" + position + ",wasSelected:" + wasSelected);
                if (!wasSelected) {
                    switchFragment(mFragmentList.get(position), position);
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                L.v("onPositionChange:" + y);
            }
        });
    }

    private void initFragments() {
        mFragmentList.add(new DoubanHotFragment());
        mFragmentList.add(new DoubanExploreFragment());
        mFragmentList.add(new DoubanMineFragment());
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_douban_movie_main, mFragmentList.get(mCurrPos)).commit();
    }


    private void switchFragment(BaseFragment to, int toPos) {
        if (mCurrentFragment != to) {
            BaseFragment from = mFragmentList.get(mCurrPos);
            mCurrPos = toPos;
            mCurrentFragment = to;
            if (to.isAdded()) {//已经有了
                mFragmentManager.beginTransaction().hide(from).show(to).commit();
            } else {//第一次加进来
                mFragmentManager.beginTransaction().hide(from).add(R.id.fl_douban_movie_main, to).commit();
            }
        }
    }
}
