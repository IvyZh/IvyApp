package com.ivy.ivyapp.activities;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.fragments.O1Fragment;
import com.ivy.ivyapp.fragments.O2Fragment;
import com.ivy.ivyapp.fragments.O3Fragment;
import com.ivy.ivyapp.fragments.O4Fragment;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private int mCurrPos = 0;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();

    protected boolean useThemestatusBarColor = false;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useStatusBarColor = true;//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置


    @Override
    protected int getContentView() {
        //StatusBarUtils.getStatusBarHeight(this);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mActionBar.hide();
        initBottomNavigation();
        initFragments();
        //StatusBarUtils.setStatusTextColor(true, this);
        StatusBarUtils.setStatusBar(this, false, false);
        StatusBarUtils.setStatusTextColor(true, MainActivity.this);
    }

    private void initBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.mipmap.ic_maps_place, R.color.color_tab_2);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.mipmap.ic_maps_local_bar, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.mipmap.ic_maps_local_restaurant, R.color.color_tab_2);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

// Add or remove notification for each item
//        bottomNavigation.setNotification("1", 1);
// OR
//        AHNotification notification = new AHNotification.Builder()
//                .setText("1")
//                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_back))
//                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_text))
//                .build();
//        bottomNavigation.setNotification(notification, 1);

// Enable / disable item & set disable color
//        bottomNavigation.enableItemAtPosition(2);
//        bottomNavigation.disableItemAtPosition(2);
//        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                L.v("setOnTabSelectedListener:" + position + ",wasSelected:" + wasSelected);
                if (!wasSelected) {
                    switchFragment(mFragmentList.get(position), position);
                    if (position == 0) {
                        StatusBarUtils.setStatusTextColor(true, MainActivity.this);
                    } else if (position == 1) {
                        StatusBarUtils.setStatusTextColor(true, MainActivity.this);
                    } else if (position == 2) {
                        StatusBarUtils.setStatusTextColor(false, MainActivity.this);
                    }
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
        mFragmentList.add(new O1Fragment());
        mFragmentList.add(new O2Fragment());
        mFragmentList.add(new O3Fragment());
        mFragmentList.add(new O4Fragment());
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_app_main, mFragmentList.get(mCurrPos)).commit();
    }


    private void switchFragment(BaseFragment to, int toPos) {
        if (mCurrentFragment != to) {
            BaseFragment from = mFragmentList.get(mCurrPos);
            mCurrPos = toPos;
            mCurrentFragment = to;
            if (to.isAdded()) {//已经有了
                mFragmentManager.beginTransaction().hide(from).show(to).commit();
            } else {//第一次加进来
                mFragmentManager.beginTransaction().hide(from).add(R.id.fl_app_main, to).commit();
            }
        }
    }


}
