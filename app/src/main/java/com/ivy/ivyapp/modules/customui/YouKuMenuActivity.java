package com.ivy.ivyapp.modules.customui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/5/17.
 */

public class YouKuMenuActivity extends BaseActivity {
    @BindView(R.id.btn_home)
    ImageButton mBtnHome;
    @BindView(R.id.rl_level1)
    RelativeLayout mRlLevel1;
    @BindView(R.id.btn_menu)
    ImageButton mBtnMenu;
    @BindView(R.id.rl_level2)
    RelativeLayout mRlLevel2;
    @BindView(R.id.rl_level3)
    RelativeLayout mRlLevel3;

    boolean isLevel3show = true;
    boolean isLevel2show = true;
    boolean isLevel1show = true;
    private long duration = 1000;
    private long offset = 200;
    private int animationCount = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_youku_menu;
    }

    @Override
    protected void initView() {

        mActionBar.setTitle("优酷菜单");
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }


    @OnClick({R.id.btn_home, R.id.btn_menu})
    public void onViewClicked(View view) {
        if (animationCount > 0) {
            L.v("count:" + animationCount);
            return;
        }
        switch (view.getId()) {
            case R.id.btn_home:
                if (isLevel3show) {
                    roateOut(mRlLevel3);
                    roateOut(mRlLevel2, offset);
                    isLevel3show = false;
                    isLevel2show = false;
                } else {
                    if (isLevel2show) {
                        roateOut(mRlLevel2);
                    } else {
                        roateIn(mRlLevel2);
                    }
                    isLevel2show = !isLevel2show;
                }
                break;
            case R.id.btn_menu:
                if (isLevel3show) {
                    roateOut(mRlLevel3);
                } else {
                    roateIn(mRlLevel3);
                }
                isLevel3show = !isLevel3show;
                break;
        }
    }

    private void roateIn(ViewGroup view) {
        roateIn(view, 0);
    }

    private void roateIn(ViewGroup view, long offset) {
        // 由于是补间动画-控件还在原有位置
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setEnabled(true);
        }

        RotateAnimation animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setStartOffset(offset);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationCount++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationCount--;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    private void roateOut(ViewGroup view) {
        roateOut(view, 0);
    }

    private void roateOut(ViewGroup view, long offset) {

        // 由于是补间动画-控件还在原有位置
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setEnabled(false);
        }


        RotateAnimation animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setStartOffset(offset);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationCount++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationCount--;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        L.v("onCreateOptionsMenu");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            UIUtils.showToast("menu");
            if (animationCount > 0)
                return true;
            if (isLevel3show) {
                roateOut(mRlLevel3);
                roateOut(mRlLevel2, offset);
                roateOut(mRlLevel1, offset * 2);
                isLevel3show = false;
                isLevel2show = false;
                isLevel1show = false;
            } else if (isLevel2show) {
                roateOut(mRlLevel2, offset);
                roateOut(mRlLevel1, offset * 2);
                isLevel2show = false;
                isLevel1show = false;
            } else if (isLevel1show) {
                roateOut(mRlLevel1, offset * 2);
                isLevel1show = false;
            } else {
                roateIn(mRlLevel1);
                roateIn(mRlLevel2, offset);
                roateIn(mRlLevel3, offset * 2);
                isLevel3show = true;
                isLevel2show = true;
                isLevel1show = true;
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
